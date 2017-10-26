package org.opentosca.toscana.core.transformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

import org.opentosca.toscana.core.BaseSpringTest;
import org.opentosca.toscana.core.csar.Csar;
import org.opentosca.toscana.core.dummy.DummyCsar;
import org.opentosca.toscana.core.dummy.ExecutionDummyPlugin;
import org.opentosca.toscana.core.testdata.TestCsars;
import org.opentosca.toscana.core.testdata.TestPlugins;
import org.opentosca.toscana.core.transformation.artifacts.ArtifactService;
import org.opentosca.toscana.core.transformation.platform.Platform;
import org.opentosca.toscana.core.transformation.properties.Property;
import org.opentosca.toscana.core.transformation.properties.PropertyType;
import org.opentosca.toscana.core.transformation.properties.RequirementType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransformationServiceImplTest extends BaseSpringTest {

    @Autowired
    private TransformationService service;
    @Autowired
    private TestCsars testCsars;
    @Autowired
    private ArtifactService ams;

    private Csar csar;

    private ExecutionDummyPlugin passingDummy = TestPlugins.PASSING_DUMMY;
    private ExecutionDummyPlugin failingDummy = TestPlugins.FAILING_DUMMY;
    private ExecutionDummyPlugin passingDummyFw = TestPlugins.PASSING_WRITING_DUMMY;
    private ExecutionDummyPlugin failingDummyFw = TestPlugins.FAILING_WRITING_DUMMY;

    @Before
    public void setUp() throws FileNotFoundException {
        csar = testCsars.getCsar(TestCsars.CSAR_YAML_VALID_DOCKER_SIMPLETASK);
    }

    @Test
    public void createTransformation() throws Exception {
        service.createTransformation(csar, TestPlugins.PLATFORM1);
        Transformation expected = new TransformationImpl(csar, TestPlugins.PLATFORM1);
        assertTrue(csar.getTransformations().containsValue(expected));
    }

    @Test
    public void startTransformationInvalidState() throws Exception {
        service.createTransformation(csar, TestPlugins.PLATFORM1);
        Transformation t = csar.getTransformations().get(TestPlugins.PLATFORM1.id);
        t.setState(TransformationState.ERROR);
        assertTrue(!service.startTransformation(t));
    }

    @Test
    public void startTransformationPropertiesNotSet() throws Exception {
        DummyCsar csar = new DummyCsar("test");
        csar.modelSpecificProperties = new HashSet<>();
        csar.modelSpecificProperties
            .add(new Property("test", PropertyType.TEXT, RequirementType.TRANSFORMATION));
        service.createTransformation(csar, passingDummy.getPlatformDetails());
        Transformation t = csar.getTransformations().get("passing");
        assertTrue(!service.startTransformation(t));
    }

    @Test
    public void transformationCreationNoProps() throws Exception {
        service.createTransformation(csar, passingDummy.getPlatformDetails());
        assertTrue(csar.getTransformations().get("passing") != null);
        Transformation t = csar.getTransformations().get("passing");
        assertEquals(TransformationState.CREATED, t.getState());
    }

    @Test
    public void transformationCreationInputNeeded() throws Exception {
        DummyCsar csar = new DummyCsar("test");
        csar.modelSpecificProperties = new HashSet<>();
        csar.modelSpecificProperties
            .add(new Property("test", PropertyType.TEXT, RequirementType.TRANSFORMATION));
        service.createTransformation(csar, passingDummy.getPlatformDetails());
        assertTrue(csar.getTransformations().get("passing") != null);
        Transformation t = csar.getTransformations().get("passing");
        assertEquals(TransformationState.INPUT_REQUIRED, t.getState());
    }

    @Test
    public void startTransformation() throws Exception {
        startTransfomationInternal(TransformationState.DONE, passingDummy.getPlatformDetails());
    }

    @Test
    public void startTransformationExecutionFail() throws Exception {
        startTransfomationInternal(TransformationState.ERROR, failingDummy.getPlatformDetails());
    }

    @Test
    public void startTransformationWithArtifacts() throws Exception {
        Transformation transformation = startTransfomationInternal(TransformationState.DONE, passingDummyFw.getPlatformDetails());
        String id = passingDummyFw.getIdentifier();

        lookForArtifactArchive(transformation);
    }

    @Test
    public void startTransformationWithArtifactsExecutionFail() throws Exception {
        startTransfomationInternal(TransformationState.ERROR, failingDummyFw.getPlatformDetails());
    }

    @Test
    public void executionStopWithSleep() throws Exception {
        service.createTransformation(csar, passingDummy.getPlatformDetails());
        Transformation t = csar.getTransformations().get("passing");
        assertTrue(service.startTransformation(t));
        letTimePass();
        assertTrue(t.getState() == TransformationState.TRANSFORMING);
        assertTrue(service.abortTransformation(t));
        letTimePass();
        assertTrue("Transformation State is " + t.getState(),
            t.getState() == TransformationState.ERROR);
    }

    @Test
    public void executionStopWhenAlreadyDone() throws Exception {
        //Start a passing transformation
        startTransformation();
        //Wait for it to finish
        Transformation t = csar.getTransformations().get("passing");
        assertTrue(!service.abortTransformation(t));
    }

    @Test
    public void stopNotStarted() throws Exception {
        transformationCreationNoProps();
        Transformation t = csar.getTransformations().get("passing");
        assertTrue(!service.abortTransformation(t));
    }

    @Test
    public void deleteTransformation() throws Exception {
        Transformation transformation = new TransformationImpl(csar, TestPlugins.PLATFORM1);
        csar.getTransformations().put(TestPlugins.PLATFORM1.id, transformation);
        service.deleteTransformation(transformation);

        assertFalse(csar.getTransformations().containsValue(transformation));
    }

    private Transformation startTransfomationInternal(TransformationState expectedState, Platform platform) throws InterruptedException, FileNotFoundException {
        Csar csar = testCsars.getCsar(TestCsars.CSAR_YAML_VALID_DOCKER_SIMPLETASK);
        service.createTransformation(csar, platform);
        Transformation t = csar.getTransformations().get(platform.id);
        assertTrue(service.startTransformation(t));
        letTimePass();
        waitForTransformationStateChange(t);
        assertEquals(expectedState, t.getState());
        return t;
    }

    private void waitForTransformationStateChange(Transformation t) throws InterruptedException {
        while (t.getState() == TransformationState.TRANSFORMING) {
            letTimePass();
        }
    }

    public void lookForArtifactArchive(Transformation transformation) {
        String filename = transformation.getCsar().getIdentifier() + "-" + transformation.getPlatform().id + "_";
        boolean found = false;
        for (File file : ams.getArtifactDir().listFiles()) {
            if (file.getName().startsWith(filename)) {
                found = true;
                break;
            }
        }
        assertTrue("Could not find artifact ZIP in Folder", found);
    }

    private void letTimePass() throws InterruptedException {
        Thread.sleep(25);
    }
}
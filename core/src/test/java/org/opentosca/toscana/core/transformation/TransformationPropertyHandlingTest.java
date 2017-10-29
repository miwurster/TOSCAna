package org.opentosca.toscana.core.transformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.opentosca.toscana.core.BaseJUnitTest;
import org.opentosca.toscana.core.dummy.DummyCsar;
import org.opentosca.toscana.core.testutils.CategoryAwareJUnitRunner;
import org.opentosca.toscana.core.transformation.logging.Log;
import org.opentosca.toscana.core.transformation.platform.Platform;
import org.opentosca.toscana.core.transformation.properties.Property;
import org.opentosca.toscana.core.transformation.properties.PropertyType;
import org.opentosca.toscana.core.transformation.properties.RequirementType;

import java.io.File;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransformationPropertyHandlingTest extends BaseJUnitTest {

    private TransformationImpl transformation;
    @Mock
    private Log log;

    @Before
    public void setUp() throws Exception {
        DummyCsar csar = new DummyCsar("dummy");
        HashSet<Property> props = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            props.add(
                new Property(
                    "prop-" + i,
                    PropertyType.UNSIGNED_INTEGER,
                    RequirementType.TRANSFORMATION,
                    "No real Description",
                    i < 5 //Only mark the first 5 properties as required
                )
            );
        }
        for (int i = 0; i < 10; i++) {
            props.add(new Property("prop-deploy-" + i, PropertyType.UNSIGNED_INTEGER, RequirementType.DEPLOYMENT));
        }
        Platform p = new Platform("test", "Test Platform", props);
        transformation = new TransformationImpl(csar, p, log);
    }

    @Test
    public void setValidProperty() throws Exception {
        for (int i = 0; i < 10; i++) {
            transformation.setProperty("prop-" + i, "1");
        }
        Map<String, String> property = transformation.getProperties().getPropertyValues();
        for (int i = 0; i < 10; i++) {
            assertEquals("1", property.get("prop-" + i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidPropertyValue() throws Exception {
        transformation.setProperty("prop-1", "-13");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidPropertyKey() throws Exception {
        transformation.setProperty("prop-112", "-13");
    }

    @Test
    public void checkAllPropsSetFalse() throws Exception {
        for (int i = 0; i < 9; i++) {
            transformation.setProperty("prop-" + i, "1");
        }
        assertFalse(transformation.allPropertiesSet(RequirementType.TRANSFORMATION));
    }

    @Test
    public void checkAllPropsSetTrue() throws Exception {
        for (int i = 0; i < 10; i++) {
            transformation.setProperty("prop-" + i, "1");
        }
        assertTrue(transformation.allPropertiesSet(RequirementType.TRANSFORMATION));
    }

    @Test
    public void checkAllRequiredPropertiesTrue() throws Exception {
        for (int i = 0; i < 5; i++) {
            transformation.setProperty("prop-" + i, "1");
        }
        assertTrue(transformation.allRequiredPropertiesSet(RequirementType.TRANSFORMATION));
        assertFalse(transformation.allPropertiesSet(RequirementType.TRANSFORMATION));
    }

    @Test
    public void checkAllRequiredPropertiesFalse() throws Exception {
        for (int i = 0; i < 4; i++) {
            transformation.setProperty("prop-" + i, "1");
        }
        assertFalse(transformation.allRequiredPropertiesSet(RequirementType.TRANSFORMATION));
        assertFalse(transformation.allPropertiesSet(RequirementType.TRANSFORMATION));
    }

    @Test
    public void checkEmptyProperties() throws Exception {
        DummyCsar csar = new DummyCsar("dummy");
        this.transformation = new TransformationImpl(csar, new Platform("test", "test", new HashSet<>()), log); 
        for (RequirementType type : RequirementType.values()) {
            assertTrue(transformation.allRequiredPropertiesSet(type));
            assertTrue(transformation.allPropertiesSet(type));
        }
    }
}

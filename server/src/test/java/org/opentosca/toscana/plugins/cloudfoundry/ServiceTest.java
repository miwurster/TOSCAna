package org.opentosca.toscana.plugins.cloudfoundry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opentosca.toscana.core.BaseUnitTest;
import org.opentosca.toscana.core.plugin.PluginFileAccess;
import org.opentosca.toscana.core.plugin.lifecycle.AbstractLifecycle;
import org.opentosca.toscana.plugins.cloudfoundry.application.Application;
import org.opentosca.toscana.plugins.cloudfoundry.application.Provider;
import org.opentosca.toscana.plugins.cloudfoundry.application.ServiceTypes;
import org.opentosca.toscana.plugins.cloudfoundry.client.Connection;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeNotNull;
import static org.opentosca.toscana.plugins.cloudfoundry.FileCreator.FILEPRAEFIX_DEPLOY;
import static org.opentosca.toscana.plugins.cloudfoundry.FileCreator.FILESUFFIX_DEPLOY;
import static org.opentosca.toscana.plugins.cloudfoundry.FileCreator.deploy_name;

public class ServiceTest extends BaseUnitTest {

    public final static String CF_ENVIRONMENT_USER = "TEST_CF_USER";
    public final static String CF_ENVIRONMENT_PW = "TEST_CF_PW";
    public final static String CF_ENVIRONMENT_HOST = "TEST_CF_HOST";
    public final static String CF_ENVIRONMENT_ORGA = "TEST_CF_ORGA";
    public final static String CF_ENVIRONMENT_SPACE = "TEST_CF_SPACE";

    private Connection connection;
    private Application app;
    private FileCreator fileCreator;

    private String envUser;
    private String envPw;
    private String envHost;
    private String envOrga;
    private String envSpace;

    private File targetDir;
    private final String appName = "testapp";
    private final String expectedDeployContent = "cf create-service cleardb spark my_db";
    private final String outputPath = AbstractLifecycle.SCRIPTS_DIR_PATH;
    private final Provider provider = new Provider(Provider
        .CloudFoundryProviderType.PIVOTAL);
    private Application myApp = new Application(1);
    private PluginFileAccess fileAccess;

    @Before
    public void setUp() {
        envUser = System.getenv(CF_ENVIRONMENT_USER);
        envPw = System.getenv(CF_ENVIRONMENT_PW);
        envHost = System.getenv(CF_ENVIRONMENT_HOST);
        envOrga = System.getenv(CF_ENVIRONMENT_ORGA);
        envSpace = System.getenv(CF_ENVIRONMENT_SPACE);

        app = new Application(appName, 1);
        app.setProvider(provider);
        connection = createConnection();
        app.setConnection(connection);

        File sourceDir = new File(tmpdir, "sourceDir");
        targetDir = new File(tmpdir, "targetDir");
        sourceDir.mkdir();
        targetDir.mkdir();
        fileAccess = new PluginFileAccess(sourceDir, targetDir, logMock());
    }

    @Test
    public void checkService() throws Exception {
        assumeNotNull(envUser, envHost, envOrga, envPw, envSpace);

        app.addService("my_db", ServiceTypes.MYSQL);
        List<Application> applications = new ArrayList<>();
        applications.add(app);
        fileCreator = new FileCreator(fileAccess, applications);

        fileCreator.createFiles();
        File targetFile = new File(targetDir, outputPath + FILEPRAEFIX_DEPLOY + deploy_name + FILESUFFIX_DEPLOY);
        String deployContent = FileUtils.readFileToString(targetFile);
        assertThat(deployContent, CoreMatchers.containsString(expectedDeployContent));
    }

    private Connection createConnection() {
        assumeNotNull(envUser, envHost, envOrga, envPw, envSpace);
        connection = new Connection(envUser,
            envPw,
            envHost,
            envOrga,
            envSpace);

        return connection;
    }
}


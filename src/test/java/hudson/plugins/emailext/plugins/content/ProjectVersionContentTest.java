package hudson.plugins.emailext.plugins.content;

import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSet;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * User: Wursteisen David
 * Date: 07/03/12
 * Time: 19:38
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MavenModuleSet.class)
public class ProjectVersionContentTest {

    @Mock
    private AbstractBuild build;

    @Mock
    private MavenModule mavenModuleMock;

    @Mock
    private FreeStyleProject freeStyleProjectMock;

    private ProjectVersionContent projectVersionContent;

    @Before
    public void setUp() {
        projectVersionContent = new ProjectVersionContent();
    }

    @Test
    public void can_get_version_from_maven_project() throws IOException, InterruptedException {
        Mockito.doReturn("1.2.5").when(mavenModuleMock).getVersion();
        Mockito.doReturn(mavenModuleMock).when(build).getProject();
        assertEquals("1.2.5", projectVersionContent.getContent(build, null, null, null));
    }

    @Test
    public void can_get_no_version_from_non_maven_projet() throws Exception {
        Mockito.doReturn(freeStyleProjectMock).when(build).getProject();
        assertEquals("", projectVersionContent.getContent(build, null, null, null));
    }

    @Ignore
    @Test
    public void can_get_version_from_root_maven_projet() throws Exception {
        // Whitebox.setInternalState(MavenModuleSet.class, "DESCRIPTOR", (Object)null);
        PowerMockito.suppress(PowerMockito.fields(MavenModuleSet.class, "DESCRIPTOR"));
        MavenModuleSet mavenModuleSetMock = PowerMockito.mock(MavenModuleSet.class);

        Mockito.doReturn("1.2.5").when(mavenModuleMock).getVersion();
        Mockito.doReturn(mavenModuleMock).when(mavenModuleSetMock).getRootModule();
        Mockito.doReturn(mavenModuleSetMock).when(build).getProject();
        assertEquals("1.2.5", projectVersionContent.getContent(build, null, null, null));

    }
}

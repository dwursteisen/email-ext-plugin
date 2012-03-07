package hudson.plugins.emailext.plugins.content;

import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSet;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.plugins.emailext.EmailType;
import hudson.plugins.emailext.ExtendedEmailPublisher;
import hudson.plugins.emailext.plugins.EmailContent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * User: Wursteisen David
 * Date: 07/03/12
 * Time: 19:39
 */
public class ProjectVersionContent implements EmailContent {

    private static final String TOKEN = "PROJECT_VERSION";

    public String getToken() {
        return TOKEN;
    }

    public List<String> getArguments() {
        return Collections.emptyList();
    }

    public String getHelpText() {
        return "Display maven project version (root module version for multi-module projects)";
    }

    public <P extends AbstractProject<P, B>, B extends AbstractBuild<P, B>> String getContent(AbstractBuild<P, B> build, ExtendedEmailPublisher publisher, EmailType emailType, Map<String, ?> args) throws IOException, InterruptedException {
        /*
         this code portion is inspired from this followed gist (author: Jean-Christophe Sirot)
        https://gist.github.com/1992647
        */
        AbstractProject project = build.getProject();
        if (project instanceof MavenModule) {
            return ((MavenModule) project).getVersion();
        } else if (project instanceof MavenModuleSet) {
            MavenModule rootModule = ((MavenModuleSet) project).getRootModule();
            if (rootModule != null) {
                return rootModule.getVersion();
            }
        }
        return "";

    }

    public boolean hasNestedContent() {
        return false;
    }
}

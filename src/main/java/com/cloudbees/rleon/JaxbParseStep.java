package com.cloudbees.rleon;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * A simple build step to check if the failure is caught during a build
 */
public class JaxbParseStep extends Builder implements SimpleBuildStep {
    private final String text;

    @DataBoundConstructor
    public JaxbParseStep(String text) {
        this.text = text;
    }

    @Restricted(NoExternalUse.class)
    public String getText() {
        return text;
    }

    @Override
    public void perform(Run build,
                        FilePath workspace,
                        Launcher launcher,
                        TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("I'm going to parse: " + text);
        String parsed = new String(JaxbDependency.parse(text));

        listener.getLogger().println("Parsed text: " + parsed);
    }

    @Extension
    @Symbol("parsetext")
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Parse a text with javax.xml.bind.DatatypeConverter.parseBase64Binary";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> t) {
            return true;
        }
    }
}

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
public class EncodeBuildStep extends Builder implements SimpleBuildStep {
    private final String text;

    @DataBoundConstructor
    public EncodeBuildStep(String text) {
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

        listener.getLogger().println("I'm going to encode: " + text);
        String encoded = Encoder.encode(text);
        listener.getLogger().println("Encoded text: " + encoded);
    }

    @Extension
    @Symbol("encodetext")
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Parse text using sun.misc.BASE64Encoder";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> t) {
            return true;
        }
    }
}

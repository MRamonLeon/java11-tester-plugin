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
public class LoadClassUsingThreadCLStep extends Builder implements SimpleBuildStep {
    private final String classToLoad;

    @DataBoundConstructor
    public LoadClassUsingThreadCLStep(String classToLoad) {
        this.classToLoad = classToLoad;
    }

    @Restricted(NoExternalUse.class)
    public String getClassToLoad() {
        return classToLoad;
    }

    @Override
    public void perform(Run build,
                        FilePath workspace,
                        Launcher launcher,
                        TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("I'm going to load: " + classToLoad);
        Class c;
        try {
            c = Thread.currentThread().getContextClassLoader().loadClass(classToLoad);
        } catch (ClassNotFoundException e) {
            throw new IOException("Class not found: " + classToLoad, e);
        }

        listener.getLogger().println("Loaded class: " + c.getClass().getCanonicalName());
    }

    @Extension
    @Symbol("loadclassusingthreadcl")
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Load class using thread cl";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> t) {
            return true;
        }
    }
}

package com.cloudbees.rleon;
import hudson.Extension;
import hudson.model.ManagementLink;
import hudson.model.PeriodicWork;
import hudson.util.VersionNumber;
import jenkins.model.Jenkins;
import jenkins.security.stapler.StaplerDispatchable;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.HttpResponses;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.compression.CompressionFilter;
import org.kohsuke.stapler.verb.GET;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Extension
@Symbol("java11tester")
public class Java11Tester extends ManagementLink {
    @CheckForNull
    @Override
    public String getIconFileName() {
        return "gear2.png";
    }

    @CheckForNull
    @Override
    public String getDisplayName() {
        return "Java11 Tester without dependencies";
    }

    @CheckForNull
    @Override
    public String getUrlName() {
        return "java11-tester";
    }

    public String getText(StaplerRequest req) {
        return req.getParameter("text");
    }

    @StaplerDispatchable
    public HttpResponse doIndex(StaplerRequest req) {
        StringBuilder s = new StringBuilder(300);
        s.append("<html><body>");

        s.append("<ul><li>Parse 'a text to parse': ");
        s.append("<a href=\"parseText?text=a%20text%20to%20parse\">Click to parse text</a>");

        s.append("</li><li>Load 'javax.xml.bind.DatatypeConverter' class: ");
        s.append("<a href=\"loadClass?class=javax.xml.bind.DatatypeConverter\">Click to load the class</a>");

        s.append("</li></ul></body></html>");

        return HttpResponses.literalHtml(s.toString());
    }

    @StaplerDispatchable
    public HttpResponse doParseText(StaplerRequest req) {
        if(req.hasParameter("text")) {
            StringBuilder s = new StringBuilder(300);
            s.append("<html><body>");

            s.append("<ul><li>Text: ");
            s.append(req.getParameter("text"));

            s.append("</li><li>Parsed: ");
            s.append(new String(JaxbDependency.parse(req.getParameter("text"))));
            s.append("</li></ul></body></html>");

            return HttpResponses.literalHtml(s.toString());
        } else {
            return HttpResponses.errorWithoutStack(500, "Pass the 'text' param");
        }
    }

    @StaplerDispatchable
    public HttpResponse doLoadClass(StaplerRequest req) throws ClassNotFoundException {
        if(req.hasParameter("class")) {
            StringBuilder s = new StringBuilder(300);
            s.append("<html><body>");

            s.append("<ul><li>Class: ");
            s.append(req.getParameter("class"));

            s.append("</li><li>Loaded: ");
            Class c = Thread.currentThread().getContextClassLoader().loadClass(req.getParameter("class"));
            s.append(c.getCanonicalName());

            s.append("</li></ul></body></html>");
            return HttpResponses.literalHtml(s.toString());
        } else {
            return HttpResponses.errorWithoutStack(500, "Pass the 'class' param");
        }
    }
}

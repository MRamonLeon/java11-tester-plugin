package com.cloudbees.rleon;
import hudson.Extension;
import hudson.model.ManagementLink;
import jenkins.security.stapler.StaplerDispatchable;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.HttpResponses;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;

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
        s.append("<html><body>\n\n");

        s.append("<ul>\n\t<li>Parse 'a text to parse': ");
        s.append("<a href=\"parseText?text=a%20text%20to%20parse\">Click to parse text</a></li>\n");

        s.append("\t<li>Load 'javax.xml.bind.DatatypeConverter' class using getClass().getClassLoader(): ");
        s.append("<a href=\"loadClassUsingClassCL?class=sun.misc.BASE64Encoder\">Click to load the class</a>\n");
        s.append("\t<i>You can load whatever class requesting loadClassUsingClassCL?class=whatever</i></li>\n");

        s.append("\t<li>Load 'javax.xml.bind.DatatypeConverter' class using Thread.currentThread().getContextClassLoader(): ");
        s.append("<a href=\"loadClassUsingThreadContextCL?class=sun.misc.BASE64Encoder\">Click to load the class</a>\n");
        s.append("\t<i>You can load whatever class requesting loadClassUsingThreadContextCL?class=whatever</i></li>\n");

        s.append("</ul>\n\n</body></html>");

        return HttpResponses.literalHtml(s.toString());
    }

    @StaplerDispatchable
    public HttpResponse doParseText(StaplerRequest req) {
        if(req.hasParameter("text")) {
            StringBuilder s = new StringBuilder(300);
            s.append("<html><body>");

            s.append("\n\n<ul>\t<li>Text: ");
            s.append(req.getParameter("text"));

            s.append("</li>\n\t<li>Parsed: ");
            s.append(Encoder.encode(req.getParameter("text")));
            s.append("</li>\n</ul>\n\n</body></html>");

            return HttpResponses.literalHtml(s.toString());
        } else {
            return HttpResponses.errorWithoutStack(500, "Pass the 'text' param");
        }
    }

    @StaplerDispatchable
    public HttpResponse doLoadClassUsingThreadContextCL(StaplerRequest req) throws ClassNotFoundException {
        if(req.hasParameter("class")) {
            StringBuilder s = new StringBuilder(300);
            s.append("<html><body>\n\n");

            s.append("<ul>\n\t<li>Class: ");
            s.append(req.getParameter("class"));

            s.append("</li>\n\t<li>Loaded: ");
            String className = req.getParameter("class");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            System.out.format("Try to load class %s using %s%n", req.getParameter("class"), classLoader);
            Class c = classLoader.loadClass(className);
            s.append(c.getCanonicalName());

            s.append("</li>\n</ul>\n\n</body></html>");
            return HttpResponses.literalHtml(s.toString());
        } else {
            return HttpResponses.errorWithoutStack(500, "Pass the 'class' param");
        }
    }
    @StaplerDispatchable
    public HttpResponse doLoadClassUsingClassCL(StaplerRequest req) throws ClassNotFoundException {
        if(req.hasParameter("class")) {
            StringBuilder s = new StringBuilder(300);
            s.append("<html><body>\n\n");

            s.append("<ul>\n\t<li>Class: ");
            s.append(req.getParameter("class"));

            s.append("</li>\n\t<li>Loaded: ");
            String className = req.getParameter("class");
            ClassLoader classLoader = getClass().getClassLoader();
            System.out.format("Try to load class %s using %s%n", req.getParameter("class"), classLoader);
            Class c = classLoader.loadClass(className);
            s.append(c.getCanonicalName());

            s.append("</li>\n</ul>\n\n</body></html>");
            return HttpResponses.literalHtml(s.toString());
        } else {
            return HttpResponses.errorWithoutStack(500, "Pass the 'class' param");
        }
    }
}

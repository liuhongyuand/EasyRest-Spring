package com.simpacct.framework.core.services.mail.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;

/**
 * Created by yu on 9/24/16.
 */
public class Configurations {
    private static Configuration cfg = null;
    private static final String DEFAUT_CHARSET = "UTF-8";

    public static void init() throws IOException {
        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.25) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        cfg.setDirectoryForTemplateLoading(new File("F:\\Delivery\\Simpacct\\AppUI\\app"));
        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");
        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

    }

    public static String render(final String templateFile, final Model model) {
        Template temp = null;
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream(); final Writer out = new OutputStreamWriter(output)) {
            temp = cfg.getTemplate(templateFile);
            temp.setEncoding(DEFAUT_CHARSET);
            temp.process(model.getRoot(), out);
            return new String(output.toByteArray(), DEFAUT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) throws IOException {
        init();
        final Model model = new Model();
        model.add("username", "其实这一个是一个新的世界,非常的棒。赞一个");
        System.out.println(render("template.html", model));

    }
}

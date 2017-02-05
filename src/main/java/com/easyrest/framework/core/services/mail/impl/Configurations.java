package com.easyrest.framework.core.services.mail.impl;

import com.easyrest.framework.core.utils.LogUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

/**
 * Created by yu on 9/24/16.
 */
class Configurations {
    private static Configuration cfg = null;
    private static final String DEFAULT_CHARSET = "UTF-8";

    static void init() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setDirectoryForTemplateLoading(new File(System.getProperties().getProperty("user.dir") + "/simpacct-server/src/main/java/com/simpacct/framework/core/services/mail/impl"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);

    }

    static String render(final String templateFile, final Model model) {
        Template temp;
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream(); final Writer out = new OutputStreamWriter(output)) {
            temp = cfg.getTemplate(templateFile);
            temp.setEncoding(DEFAULT_CHARSET);
            temp.process(model.getRoot(), out);
            return new String(output.toByteArray(), DEFAULT_CHARSET);
        } catch (Exception e) {
            LogUtils.error(e.getMessage(), e);
        }
        return "";
    }

}

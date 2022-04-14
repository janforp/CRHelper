package com.janita.plugin.cr.export;

import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.janita.plugin.cr.domain.CrQuestion;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MDFreemarkProcessor
 *
 * @author zhucj
 * @since 20220324
 */
public class MDFreeMarkProcessor {

    private Template getTemplate() throws IOException {
        Configuration configuration = new Configuration();
        URL resource = MDFreeMarkProcessor.class.getResource("/template/md.ftl");
        if (resource == null) {
            return null;
        }
        String loadText = UrlUtil.loadText(resource);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("MDTemplate", loadText);
        configuration.setTemplateLoader(templateLoader);
        return configuration.getTemplate("MDTemplate");
    }

    private Object getModel(String fileName, List<CrQuestion> questionList) {
        Map<String, Object> model = new HashMap<>();
        model.put("fileName", fileName);
        model.put("questionList", questionList);
        return model;
    }

    private Writer getWriter(String fullPath) throws Exception {
        File file = new File(fullPath);
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
    }

    public void process(String fileName, String fullPath, List<CrQuestion> questionList) throws Exception {
        Template template = getTemplate();
        if (template == null) {
            return;
        }
        Object model = getModel(fileName, questionList);
        Writer writer = getWriter(fullPath);
        template.process(model, writer);
    }
}
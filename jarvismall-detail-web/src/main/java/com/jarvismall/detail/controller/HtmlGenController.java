package com.jarvismall.detail.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/9/16.
 * 网页静态化处理
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value(value = "${FREEMAKERR_OUT_PATH}")
    String outPath;

    @RequestMapping("/genHtml")
    @ResponseBody
    public String genHtml() {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("hello.ftl");
            Map data = new HashMap();
            data.put("hello", "Spring generator ");
            ArrayList list = new ArrayList() {
                {
                    add("a");
                    add("a2");
                    add("a3");
                    add("a4");
                    add("a5");
                    add("a6");
                }
            };
            data.put("list", list);
            data.put("dateKey", new Date());
            data.put("head", "base");

            Writer out = new FileWriter(new File(outPath));

            template.process(data, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "OK";
    }
}

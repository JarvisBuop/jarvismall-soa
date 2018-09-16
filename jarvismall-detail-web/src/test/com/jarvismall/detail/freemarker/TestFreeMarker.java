package com.jarvismall.detail.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/9/16.
 */
public class TestFreeMarker  {
    String path = "D:\\MyIdeaPro\\jarvismall-parent\\jarvismall-detail-web\\src\\main\\webapp\\WEB-INF\\ftl";
    String outPath = "D:\\MyIdeaPro\\jarvismall-parent\\jarvismall-detail-web\\src\\main\\webapp\\WEB-INF\\ftl\\template\\helloTeplate.html";
    @Test
    public void testFreeMarker() throws Exception{
        Configuration configuration =new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File(path));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("hello.ftl");

        //对象
        Map data = new HashMap();
        data.put("hello","fill_txt");
        ArrayList list = new ArrayList(){
            {
                add("a");
                add("a2");
                add("a3");
                add("a4");
                add("a5");
                add("a6");
            }
        };
        data.put("list",list);
        data.put("dateKey",new Date());
        data.put("head","base");
        //输出
        Writer out = new FileWriter(new File(outPath));
        template.process(data,out);

        out.close();
    }
}

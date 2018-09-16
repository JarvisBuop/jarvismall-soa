package com.jarvismall.detail.listener;

import com.jarvismall.detail.pojo.Item;
import com.jarvismall.pojo.ActivieMqBundle;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.pojo.TbItemDesc;
import com.jarvismall.service.TbItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/9/16.
 */
public class GenFreeMarkerListener implements MessageListener {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    TbItemService tbItemService;

    @Value("${HTML_OUT_PATH}")
    private String outPath;

    @Override
    public void onMessage(Message message) {
        try {
            //接受消息取商品添加的Id;
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            ActivieMqBundle bundle = ActivieMqBundle.createBundle(text);
            if(bundle==null) return;
            System.out.print("resultMsg: " +text);
            Long itemId = Long.parseLong(bundle.getId());
            //等待发送端事务提交
            Thread.sleep(1000);
            //根据商品Id查询表;
            Item item = new Item(tbItemService.getItemById(itemId));
            TbItemDesc itemDesc = tbItemService.getItemDescById(itemId);

            //freemarker生成静态页面;
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");

            Map data = new HashMap();
            data.put("item",item);
            data.put("itemDesc",itemDesc);
            Writer writer = new FileWriter(outPath+String.valueOf(itemId)+".html");

            template.process(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

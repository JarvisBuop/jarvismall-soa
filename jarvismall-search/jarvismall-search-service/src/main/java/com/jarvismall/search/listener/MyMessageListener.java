package com.jarvismall.search.listener;

import com.jarvismall.pojo.ActivieMqBundle;
import com.jarvismall.pojo.SearchItem;
import com.jarvismall.search.dao.SearchDao;
import com.jarvismall.search.mapper.SearchItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by JarvisDong on 2018/9/14.
 * 接受activemq的消息; 同步索引库;
 */
public class MyMessageListener implements MessageListener {
    @Autowired
    private SearchItemMapper mapper;
    @Autowired
    private SolrServer server;



    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.print("resultMsg: " +text);

            ActivieMqBundle bundle = ActivieMqBundle.createBundle(text);
            if(bundle!=null){
                System.out.print("bundle: " +bundle.toString());
                //等待发送端事务提交
                Thread.sleep(2000);
                //取id,查询数据库,更新索引库;
                long id = Long.parseLong(bundle.getId());
                SearchItem itemById = mapper.getItemById(id);

                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",itemById.getId());
                document.addField("item_title",itemById.getTitle());
                document.addField("item_sell_point",itemById.getSell_point());
                document.addField("item_category_name",itemById.getCategory_name());
                document.addField("item_image",itemById.getImage());
                document.addField("item_desc",itemById.getItem_desc());
                document.addField("item_price",itemById.getPrice());
                server.add(document);
                server.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

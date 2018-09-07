package com.jarvismall.search.service.impl;

import com.jarvismall.pojo.SearchItem;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.search.mapper.SearchItemMapper;
import com.jarvismall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/7.
 */
public class SearchItemsServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper mapper;
    @Autowired
    private SolrServer server;

    @Override
    public TaotaoResult importItems2Index() {
        //查询所有商品,遍历添加至索引库
        List<SearchItem> itemList = mapper.getItemList();
        TaotaoResult result = null;
        try {
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();

                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getItem_desc());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_desc", searchItem.getItem_desc());
                document.addField("item_category_name", searchItem.getCategory_name());

                server.add(document);
            }
            server.commit();
            result = TaotaoResult.build(200, "success import");
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.build(500, "fail import");
        }
        return result;
    }
}

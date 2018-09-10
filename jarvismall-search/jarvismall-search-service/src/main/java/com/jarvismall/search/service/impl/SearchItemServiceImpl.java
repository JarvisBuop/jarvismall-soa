package com.jarvismall.search.service.impl;

import com.jarvismall.pojo.SearchItem;
import com.jarvismall.pojo.SearchResult;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.search.dao.SearchDao;
import com.jarvismall.search.mapper.SearchItemMapper;
import com.jarvismall.search.service.SearchItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/9/7.
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper mapper;
    @Autowired
    private SolrServer server;
    @Autowired
    SearchDao searchDao;

    @Override
    public TaotaoResult importItems2Index() {
        //查询所有商品,遍历添加至索引库
        List<SearchItem> itemList = mapper.getItemList();
        TaotaoResult result = null;
        try {
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();

                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
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

    @Override
    public SearchResult getSearchItemsBySolrQuery(String queryStr, int page, int rows) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("df", "item_keywords");
        solrQuery.setQuery(queryStr);
        solrQuery.setStart(((page < 1 ? 1 : page) - 1) * rows);
        solrQuery.setRows(rows < 10 ? 10 : rows);
        if (StringUtils.isNotBlank(queryStr)) {
            solrQuery.setHighlight(true);
            solrQuery.setHighlightSimplePre("<font color='red'>");
            solrQuery.setHighlightSimplePost("</font>");
            solrQuery.addHighlightField("item_title");
        } else {
            solrQuery.setHighlight(false);
        }

        return searchDao.search(solrQuery);
    }
}

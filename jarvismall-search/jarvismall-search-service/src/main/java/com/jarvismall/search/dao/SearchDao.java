package com.jarvismall.search.dao;

import com.jarvismall.pojo.SearchItem;
import com.jarvismall.pojo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/9/8.
 * 获取数据 dao,使用solrQuery构建数据;
 */
@Repository
public class SearchDao  {
    @Autowired
    private SolrServer server;

    public SearchResult search(SolrQuery solrQuery){

        SearchResult result = new SearchResult();
        try {
            ArrayList<SearchItem> searchItems = new ArrayList();

            QueryResponse queryResponse = server.query(solrQuery);
            SolrDocumentList documents = queryResponse.getResults();
            for(SolrDocument document:documents){
                SearchItem item = new SearchItem();
                item.setId((String) document.get("id"));
                item.setTitle((String) document.get("item_title"));
                item.setSell_point((String) document.get("item_sell_point"));
                item.setCategory_name((String) document.get("item_category_name"));
                String item_image = (String) document.get("item_image");
                if(StringUtils.isNotBlank(item_image)){
                    item_image = item_image.split(",")[0];
                }
                item.setImage(item_image);
                item.setItem_desc((String) document.get("item_desc"));
                item.setPrice((Long) document.get("item_price"));

                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                List<String> strings = highlighting.get(item.getId()).get("item_title");
                if(strings!=null && strings.size()>0){
                    item.setTitle(strings.get(0));
                }
                searchItems.add(item);
            }
            result.setItemList(searchItems);
            result.setRecordCount(documents.getNumFound());
            long pages = documents.getNumFound() / solrQuery.getRows();
            if(documents.getNumFound() % solrQuery.getRows()>0){
                pages++;
            }
            result.setTotalPages(pages);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return result;
    }
}

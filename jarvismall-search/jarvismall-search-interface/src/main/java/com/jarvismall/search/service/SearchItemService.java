package com.jarvismall.search.service;

import com.jarvismall.pojo.SearchResult;
import com.jarvismall.pojo.TaotaoResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by JarvisDong on 2018/9/7.
 */
public interface SearchItemService {
    TaotaoResult importItems2Index();

    SearchResult getSearchItemsBySolrQuery(String query,int page,int rows);
}

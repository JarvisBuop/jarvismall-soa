package com.jarvismall.search.mapper;

import com.jarvismall.pojo.SearchItem;

import java.util.List;

/**
 * Created by JarvisDong on 2018/9/7.
 * 联表查询 mapper;
 */
public interface SearchItemMapper  {
    //获取搜索的所有数据,导入到solr;
    List<SearchItem> getItemList();
}

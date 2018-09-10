package com.jarvismall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/8.
 */
public class SearchResult implements Serializable{
    private Long totalPages;
    private Long recordCount;
    private List<SearchItem> itemList;

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}

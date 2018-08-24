package com.jarvismall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/19.
 * easyui 分页数据;
 */
public class EasyUiDataGridResult<T> implements Serializable {
    private Integer total;
    private List<T> rows;

    public EasyUiDataGridResult() {
    }

    public EasyUiDataGridResult(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}




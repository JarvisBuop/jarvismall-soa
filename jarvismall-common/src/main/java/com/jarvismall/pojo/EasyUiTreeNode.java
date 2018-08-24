package com.jarvismall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/20.
 * easyui树形控件
 */
public class EasyUiTreeNode implements Serializable{
    private long id;
    private String text;
    private String state;
    private List<EasyUiTreeNode> children;

    public List<EasyUiTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<EasyUiTreeNode> children) {
        this.children = children;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

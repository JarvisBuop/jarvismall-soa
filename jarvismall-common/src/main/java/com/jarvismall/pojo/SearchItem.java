package com.jarvismall.pojo;

import java.io.Serializable;

/**
 * Created by JarvisDong on 2018/9/7.
 */
public class SearchItem implements Serializable {
    //schema;
    private String id;
    private String title;
    private String sell_point;
    //bigint(*100)
    private Long price;
    private String image;
    private String item_desc;
    private String category_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
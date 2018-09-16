package com.jarvismall.detail.pojo;

import com.jarvismall.pojo.TbItem;

/**
 * Created by JarvisDong on 2018/9/15.
 */
public class Item extends TbItem {

    public Item( TbItem tbItem) {
        //初始化属性
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    //用于js中el表达式;
    public String[] getImages(){
        if(this.getImage()!=null && !"".equals(this.getImage())){
           return this.getImage().split(",");
        }
        return null;
    }
}

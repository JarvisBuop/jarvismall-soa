package com.jarvismall.detail.controller;

import com.jarvismall.detail.pojo.Item;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.pojo.TbItemDesc;
import com.jarvismall.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by JarvisDong on 2018/9/15.
 * 商品详情;
 */
@Controller
public class ItemController {
    @Autowired
    TbItemService tbItemService;

    /**
     * 商品详情;
     * search模块;
     *
     * 动态页面 jsp+redis;
     */
    @RequestMapping("/item/{id}")
    public String itemDetail(@PathVariable(value = "id") Long itemId, Model model){
        TbItem tbItem = tbItemService.getItemById(itemId);
        TbItemDesc tbItemDesc = tbItemService.getItemDescById(itemId);
        Item item = new Item(tbItem);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }
}

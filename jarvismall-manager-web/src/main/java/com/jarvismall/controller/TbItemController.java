package com.jarvismall.controller;

import com.jarvismall.pojo.TbItem;
import com.jarvismall.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JarvisDong on 2018/8/16.
 */
@Controller
public class TbItemController {
    @Autowired
    TbItemService tbItemService;

    @RequestMapping("/tbItem/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem itemById = tbItemService.getItemById(itemId);
        return itemById;
    }
}

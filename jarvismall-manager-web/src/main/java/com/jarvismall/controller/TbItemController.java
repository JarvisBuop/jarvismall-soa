package com.jarvismall.controller;

import com.jarvismall.pojo.EasyUiDataGridResult;
import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 * 通过dubbo 远程调用 Service的数据;
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

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUiDataGridResult getItemList(int page,int rows){
        EasyUiDataGridResult itemList = tbItemService.getItemList(page, rows);
        return itemList;
    }

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUiTreeNode> getTreeNodeByParentId(@RequestParam(name = "id" ,defaultValue = "0") long parentId){
        List<EasyUiTreeNode> nodes = tbItemService.getTreeNodeByParentId(parentId);
        return nodes;
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public TaotaoResult addItem(TbItem item, String desc){
        return tbItemService.addItem(item,desc);
    }
}

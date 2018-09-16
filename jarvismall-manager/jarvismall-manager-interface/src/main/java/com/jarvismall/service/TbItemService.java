package com.jarvismall.service;

import com.jarvismall.pojo.*;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 */
public interface TbItemService {
    List<TbItem> selectAll();
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);

    EasyUiDataGridResult getItemList(int page, int rows);

    List<EasyUiTreeNode> getTreeNodeByParentId(long parentId);

    TaotaoResult addItem(TbItem item,String desc);
}

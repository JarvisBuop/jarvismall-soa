package com.jarvismall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jarvismall.mapper.TbItemCatMapper;
import com.jarvismall.mapper.TbItemMapper;
import com.jarvismall.pojo.*;
import com.jarvismall.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    TbItemMapper tbItemMapper;
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItem> selectAll() {
        return null;
    }

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    @Override
    public EasyUiDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);

        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        int i = tbItemMapper.countByExample(example);
        System.out.print("count:" + i);
        Page<TbItem> pageBean = new Page<TbItem>(page, rows, i);

        return new EasyUiDataGridResult((int) pageBean.getTotal(), tbItems);
    }

    @Override
    public List<EasyUiTreeNode> getTreeNodeByParentId(long id) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        List<EasyUiTreeNode> nodes = new ArrayList<>();
        for(TbItemCat cat : tbItemCats){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setText(cat.getName());
            node.setId(cat.getId());
            //有子节点closed,没有子节点open;
            node.setState(cat.getIsParent()?"closed":"open");
            nodes.add(node);
        }
        return nodes;
    }
}

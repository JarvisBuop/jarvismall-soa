package com.jarvismall.portal.service.impl;

import com.jarvismall.mapper.TbContentCategoryMapper;
import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.pojo.TbContentCategory;
import com.jarvismall.pojo.TbContentCategoryExample;
import com.jarvismall.portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/25.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUiTreeNode> getContentCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
        List<EasyUiTreeNode> nodes = new ArrayList<>();
        for(TbContentCategory cat : tbContentCategories){
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

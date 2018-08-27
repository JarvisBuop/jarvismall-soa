package com.jarvismall.portal.service.impl;

import com.jarvismall.mapper.TbContentCategoryMapper;
import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbContentCategory;
import com.jarvismall.pojo.TbContentCategoryExample;
import com.jarvismall.portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        for (TbContentCategory cat : tbContentCategories) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setText(cat.getName());
            node.setId(cat.getId());
            //有子节点closed,没有子节点open;
            node.setState(cat.getIsParent() ? "closed" : "open");
            nodes.add(node);
        }

        return nodes;
    }

    @Override
    public TaotaoResult addCategoryItem(Long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());

        int id = contentCategoryMapper.insertSelective(tbContentCategory);

        tbContentCategory.setId((long) id);
        //父节点是叶子节点,需要改为父节点,有子类了;
        TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentCategory.getIsParent()) {
            parentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }

        TaotaoResult result = TaotaoResult.build(200, "Ok", tbContentCategory);
        return result;
    }

    @Override
    public void updataCategoryItem(Long id, String name) {
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        if (tbContentCategory != null) {
            tbContentCategory.setName(name);
            contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        }
    }

    @Override
    public void deleteCategoryItem(Long parentId, Long id) {
        //parentId不使用,id为当前节点id;
        TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);

        //父节点:有子节点判断是否删除,及递归删除所有子节点;
        //叶子节点:判断父节点是否有其他的叶子节点
        if(tbContentCategory.getIsParent()){
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
            for(TbContentCategory category:tbContentCategories){
                contentCategoryMapper.deleteByPrimaryKey(category.getId());
            }
        }else {
            TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(tbContentCategory.getParentId());
            int i = contentCategoryMapper.countByExample(example);
            if(i == 0){
                parentCategory.setIsParent(false);
            }
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
    }


}

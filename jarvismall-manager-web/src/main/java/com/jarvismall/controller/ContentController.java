package com.jarvismall.controller;

import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbContent;
import com.jarvismall.pojo.TbContentCategory;
import com.jarvismall.portal.service.CategoryService;
import com.jarvismall.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/25.
 * 内容管理;
 */
@Controller
public class ContentController {

    @Autowired
    private CategoryService mCategoryService;

    @Autowired
    private ContentService mContentService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUiTreeNode> contentCategoryList = mCategoryService.getContentCategoryList(parentId);
        return contentCategoryList;
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult addCategoryItem(Long parentId,String name){
        TaotaoResult result = mCategoryService.addCategoryItem(parentId, name);
        return result;
    }

    @RequestMapping("/content/category/update")
    public void updataCategoryItem(Long id,String name){
        mCategoryService.updataCategoryItem(id,name);
    }

    @RequestMapping("/content/category/delete")
    public void deleteCategoryItem(Long parentId,Long id){
        mCategoryService.deleteCategoryItem(parentId,id);
    }

    @RequestMapping("/content/save")
    @ResponseBody
    TaotaoResult addContentItem(TbContent content){
        return mContentService.addContentItem(content);
    }
}

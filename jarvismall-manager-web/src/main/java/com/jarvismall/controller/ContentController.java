package com.jarvismall.controller;

import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.portal.service.CategoryService;
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
    CategoryService mCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUiTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUiTreeNode> contentCategoryList = mCategoryService.getContentCategoryList(parentId);
        return contentCategoryList;
    }
}

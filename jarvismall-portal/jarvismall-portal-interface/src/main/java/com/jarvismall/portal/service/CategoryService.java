package com.jarvismall.portal.service;

import com.jarvismall.pojo.EasyUiTreeNode;
import com.jarvismall.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/25.
 */
public interface CategoryService {

    List<EasyUiTreeNode> getContentCategoryList(long parentId);

    TaotaoResult addCategoryItem(Long parentId, String name);

    void updataCategoryItem(Long id, String name);

    void deleteCategoryItem(Long parentId,Long id);
}

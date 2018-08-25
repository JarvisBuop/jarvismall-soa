package com.jarvismall.portal.service;

import com.jarvismall.pojo.EasyUiTreeNode;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/25.
 */
public interface CategoryService {

    List<EasyUiTreeNode> getContentCategoryList(long parentId);
}

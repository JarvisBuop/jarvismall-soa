package com.jarvismall.portal.service;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbContent;
import com.jarvismall.pojo.TbItem;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/26.
 */
public interface ContentService {
    TaotaoResult addContentItem(TbContent content);

    List<TbContent> getContentByCid(Long categoryId);
}

package com.jarvismall.service;

import com.jarvismall.pojo.TbItem;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 */
public interface TbItemService {
    List<TbItem> selectAll();
    TbItem getItemById(Long itemId);
}

package com.jarvismall.service.impl;

import com.jarvismall.mapper.TbItemMapper;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    TbItemMapper tbItemMapper;

    @Override
    public List<TbItem> selectAll() {
        return null;
    }

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }
}

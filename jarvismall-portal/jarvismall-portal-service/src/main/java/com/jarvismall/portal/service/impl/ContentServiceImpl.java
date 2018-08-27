package com.jarvismall.portal.service.impl;

import com.jarvismall.mapper.TbContentMapper;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbContent;
import com.jarvismall.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by JarvisDong on 2018/8/26.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;

    @Override
    public TaotaoResult addContentItem(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insertSelective(content);
        return TaotaoResult.ok();
    }
}

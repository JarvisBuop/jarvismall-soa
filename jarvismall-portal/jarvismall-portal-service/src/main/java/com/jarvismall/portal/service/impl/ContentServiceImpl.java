package com.jarvismall.portal.service.impl;

import com.jarvismall.jedis.JedisClient;
import com.jarvismall.mapper.TbContentMapper;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbContent;
import com.jarvismall.pojo.TbContentExample;
import com.jarvismall.portal.service.ContentService;
import com.jarvismall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/26.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContentItem(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insertSelective(content);
        jedisClient.hdel(INDEX_CONTENT,content.getCategoryId()+"");
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCid(Long categoryId) {
        try {
            String s = jedisClient.hget(INDEX_CONTENT,String.valueOf(categoryId));
            if (StringUtils.isNoneBlank(s)) {
                List<TbContent> redisList = JsonUtils.jsonToList(s, TbContent.class);
                if (redisList != null && redisList.size() != 0) {
                    return redisList;
                }
            }
        } catch (Exception e) {

        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(example);

        try {
            if(tbContents!=null && tbContents.size()!=0){
                jedisClient.hset(INDEX_CONTENT,String.valueOf(categoryId),JsonUtils.objectToJson(tbContents));
            }
        }catch (Exception e){

        }
        return tbContents;
    }
}

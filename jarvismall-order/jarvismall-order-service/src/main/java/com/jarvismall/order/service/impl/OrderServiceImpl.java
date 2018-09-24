package com.jarvismall.order.service.impl;

import com.jarvismall.jedis.JedisClient;
import com.jarvismall.mapper.TbOrderItemMapper;
import com.jarvismall.mapper.TbOrderMapper;
import com.jarvismall.mapper.TbOrderShippingMapper;
import com.jarvismall.order.pojo.OrderInfo;
import com.jarvismall.order.service.OrderService;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbOrderItem;
import com.jarvismall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/24.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    TbOrderMapper tbOrderMapper;

    @Autowired
    TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    JedisClient jedisClient;
    @Value("${ORDER_ID_GEN_KEY}")
    String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_BEGIN}")
    String ORDER_ID_BEGIN;

    @Value("${ORDER_ITEM_GEN_KEY}")
    String ORDER_ITEM_GEN_KEY;
    @Override
    public TaotaoResult addOrder(OrderInfo orderInfo) {
        //使用redis incr自增
        if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_ID_BEGIN);
        }
        Long incr = jedisClient.incr(ORDER_ID_GEN_KEY);

        orderInfo.setOrderId(incr.toString());
        orderInfo.setPostFee("0");
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());

        tbOrderMapper.insert(orderInfo);
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        if (orderItems != null && orderItems.size() != 0) {
            for (TbOrderItem tbOrderItem : orderItems) {

                Long incrItem = jedisClient.incr(ORDER_ITEM_GEN_KEY);
                tbOrderItem.setId(incrItem.toString());
                tbOrderItem.setOrderId(orderInfo.getOrderId());

                tbOrderItemMapper.insert(tbOrderItem);
            }
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderInfo.getOrderId());
        orderShipping.setCreated(new Date());
        orderInfo.setUpdateTime(new Date());
        tbOrderShippingMapper.insert(orderShipping);

        return TaotaoResult.ok(orderInfo.getOrderId());
    }
}

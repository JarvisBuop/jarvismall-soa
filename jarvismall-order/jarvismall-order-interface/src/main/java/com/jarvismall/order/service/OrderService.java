package com.jarvismall.order.service;

import com.jarvismall.order.pojo.OrderInfo;
import com.jarvismall.pojo.TaotaoResult;

/**
 * Created by JarvisDong on 2018/9/24.
 */

public interface OrderService {
    TaotaoResult addOrder(OrderInfo orderInfo);
}

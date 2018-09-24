package com.jarvismall.order.pojo;

import com.jarvismall.pojo.TbOrder;
import com.jarvismall.pojo.TbOrderItem;
import com.jarvismall.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/24.
 */
public class OrderInfo extends TbOrder implements Serializable{

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}

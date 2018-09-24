package com.jarvismall.order.controller;

import com.jarvismall.order.pojo.OrderInfo;
import com.jarvismall.order.service.OrderService;
import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.pojo.TbUser;
import com.jarvismall.utils.CookieUtils;
import com.jarvismall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/24.
 */
@Controller
public class OrderController {
    @Value(value = "${CART_COOKIE}")
    String CART_COOKIE;

    @Autowired
    OrderService orderService;


    @RequestMapping("/order/order-cart")
    public String showOrder(HttpServletRequest request, Model model) {
        //登录状态(由拦截器处理)
        //用户信息取收货信息,request域中后去,在sso中获取信息在拦截器中存储信息;
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {

        }
        //从cookie中取商品列表
        List<TbItem> cartItems = getCartItems(request);
        model.addAttribute("cartList", cartItems);
        return "order-cart";
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        TaotaoResult result = orderService.addOrder(orderInfo);

        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        DateTime dataTime = new DateTime();
        dataTime = dataTime.plusDays(3);
        model.addAttribute("date", dataTime.toString("yyyy-MM-dd hh:mm:ss"));
        return "success";
    }

    private List<TbItem> getCartItems(HttpServletRequest request) {
        String cookieValue = CookieUtils.getCookieValue(request, CART_COOKIE, true);
        if (StringUtils.isNotBlank(cookieValue)) {
            return JsonUtils.jsonToList(cookieValue, TbItem.class);
        } else {
            return new ArrayList<>();
        }
    }

}

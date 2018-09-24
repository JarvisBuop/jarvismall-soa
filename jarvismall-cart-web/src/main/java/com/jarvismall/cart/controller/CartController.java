package com.jarvismall.cart.controller;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.service.TbItemService;
import com.jarvismall.utils.CookieUtils;
import com.jarvismall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JarvisDong on 2018/9/23.
 */
@Controller
public class CartController {

    @Autowired
    TbItemService tbItemService;

    @Value(value = "${CART_COOKIE}")
    String CART_COOKIE;
    @Value(value = "${CART_COOKIE_TIME}")
    Integer CART_COOKIE_TIME;

    /**
     * 此种方法是 将购物车信息存在cookie中,每次从cookie中取,修改和显示;
     * <p>
     * 另一中思路:
     * 可以将购物车信息存在数据库,使用id直接查,在修改数据,返回显示;
     */
    @RequestMapping("/cart/add/{itemId}")
    public String add2Cart(@PathVariable(value = "itemId") Long id,
                           @RequestParam(defaultValue = "1") Integer num,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        //先从cookie中取商品列表,判断是否存在;
        //不存在添加商品,取商品信息;存在,修改;
        List<TbItem> cartItems = getCartItems(request);
        boolean flag = false;
        if (cartItems != null && cartItems.size() != 0) {
            for (TbItem tbItem : cartItems) {
                //有一个值就是比较值;
                if (tbItem.getId() == id.longValue()) {
                    tbItem.setNum(tbItem.getNum() + num);
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            TbItem itemById = tbItemService.getItemById(id);
            itemById.setNum(num);
            String image = itemById.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] split = image.split(",");
                if (split.length > 0) {
                    itemById.setImage(split[0]);
                }
            }
            cartItems.add(itemById);
        }
        //将购物车写入cookie,返回到cookie中;
        setCartItems(request, response, cartItems);
        return "cartSuccess";
    }

    private List<TbItem> getCartItems(HttpServletRequest request) {
        String cookieValue = CookieUtils.getCookieValue(request, CART_COOKIE, true);
        if (StringUtils.isNotBlank(cookieValue)) {
            return JsonUtils.jsonToList(cookieValue, TbItem.class);
        } else {
            return new ArrayList<>();
        }
    }

    private void setCartItems(HttpServletRequest request, HttpServletResponse response, List<TbItem> list) {
        if (list != null) {
            String s = JsonUtils.objectToJson(list);
            if (StringUtils.isNotBlank(s)) {
                CookieUtils.setCookie(request, response, CART_COOKIE, s, CART_COOKIE_TIME, true);
            }
        }
    }

    @RequestMapping("/cart/cart")
    public String cartList(HttpServletRequest request, Model model){
        //取cookie中的内容展示
        List<TbItem> cartItems = getCartItems(request);
        Long totalPrice = 0l;
        if(cartItems!=null && cartItems.size()!=0){
            for(TbItem tbItem:cartItems){
                totalPrice += tbItem.getPrice();
            }
        }
        model.addAttribute("cartList",cartItems);
//        model.addAttribute("totalPrice",totalPrice);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{itemNum}")
    @ResponseBody
    public TaotaoResult changeCart(@PathVariable(value = "itemId") Long itemId,
                                   @PathVariable(value = "itemNum") Integer itemNum,
                                   HttpServletResponse response,
                                   HttpServletRequest request){

        List<TbItem> cartItems = getCartItems(request);
        if(cartItems!=null && cartItems.size()!=0){
            for(TbItem tbItem:cartItems){
                //有一个值就是比较值;
                if (tbItem.getId() == itemId.longValue()) {
                    tbItem.setNum(itemNum);
                    break;
                }
            }
        }
        //将购物车写入cookie,返回到cookie中;
        setCartItems(request, response, cartItems);
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCart(@PathVariable(value = "itemId") Long itemId,
                             HttpServletResponse response,
                             HttpServletRequest request){
        List<TbItem> cartItems = getCartItems(request);
        if(cartItems!=null && cartItems.size()!=0){
            for(TbItem tbItem:cartItems){
                //有一个值就是比较值;
                if (tbItem.getId() == itemId.longValue()) {
                    cartItems.remove(tbItem);
                    break;
                }
            }
        }

        setCartItems(request, response, cartItems);
        return "cart";
    }
}

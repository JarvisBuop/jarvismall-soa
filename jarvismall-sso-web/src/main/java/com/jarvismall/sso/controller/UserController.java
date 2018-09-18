package com.jarvismall.sso.controller;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbUser;
import com.jarvismall.sso.service.UserService;
import com.jarvismall.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by JarvisDong on 2018/9/17.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Value("${COOKIE_KEY}")
    String COOKIE_KEY;


    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUser(@PathVariable("param") String param, @PathVariable("type") int type) {
        return userService.checkUser(param,type);
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult registerUser(TbUser tbUser) {
        return userService.registerUser(tbUser);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult loginin(String username, String password,
                                HttpServletRequest request,HttpServletResponse response){
        TaotaoResult login = userService.login(username, password);

        //token写入cookies;
        CookieUtils.setCookie(request,response,COOKIE_KEY,login.getData().toString());
        //登录成功,跳转回调;
        return login;
    }
}

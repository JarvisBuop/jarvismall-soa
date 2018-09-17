package com.jarvismall.sso.controller;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbUser;
import com.jarvismall.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JarvisDong on 2018/9/17.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

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
}

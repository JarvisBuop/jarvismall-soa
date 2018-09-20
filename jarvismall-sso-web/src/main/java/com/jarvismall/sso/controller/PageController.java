package com.jarvismall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by JarvisDong on 2018/9/19.
 */
@Controller
public class PageController {

    @RequestMapping("/page/register")
    public String showRegister(){

        return "register";
    }

    @RequestMapping("/page/login")
    public String showLogin(){

        return "login";
    }
}

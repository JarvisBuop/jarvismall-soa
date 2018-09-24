package com.jarvismall.sso.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showLogin(@RequestParam(defaultValue = "") String url, Model model){
        model.addAttribute("redirect",url);
        return "login";
    }
}

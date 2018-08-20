package com.jarvismall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by JarvisDong on 2018/8/17.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    /**
     * easy ui 请求的page
      * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){

        return page;
    }


}

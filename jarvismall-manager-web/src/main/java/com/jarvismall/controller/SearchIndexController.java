package com.jarvismall.controller;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JarvisDong on 2018/9/8.
 * 索引库维护的controller;
 */
@Controller
public class SearchIndexController  {
    @Autowired
    SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex(){
        TaotaoResult result = searchItemService.importItems2Index();
        return result;
    }
}

package com.jarvismall.search.controller;

import com.jarvismall.pojo.SearchResult;
import com.jarvismall.search.service.SearchItemService;
import com.jarvismall.search.utils.CharSetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JarvisDong on 2018/9/8.
 */
@Controller
public class SearchIndexController {
    @Autowired
    SearchItemService searchItemService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer rows,
                         Model model) {
//        int a = 1/0;
        queryString = CharSetUtils.convert(queryString);
        SearchResult result = searchItemService.getSearchItemsBySolrQuery(queryString,page,rows);
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages",result.getTotalPages());
        model.addAttribute("itemList",result.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}

package com.jarvismall.portal.controller;

import com.jarvismall.pojo.TbContent;
import com.jarvismall.portal.pojo.AdNode;
import com.jarvismall.portal.service.ContentService;
import com.jarvismall.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/24.
 */
@Controller
public class IndexController {
    @Autowired
    ContentService mService;

    @Value("${AD1_CATEGORY_ID}")
    Long categotyId;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        List<TbContent> contentByCid = mService.getContentByCid(categotyId);
        List<AdNode> adNodeList = new ArrayList<>();
        for (TbContent tbContent:contentByCid) {
            AdNode node = new AdNode();
            node.setAlt(tbContent.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            adNodeList.add(node);
        }
        String adJson = JsonUtils.objectToJson(adNodeList);
        model.addAttribute("ad1",adJson);
        return "index";
    }
}

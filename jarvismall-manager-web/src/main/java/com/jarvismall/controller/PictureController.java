package com.jarvismall.controller;

import com.jarvismall.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JarvisDong on 2018/8/23.
 */
@Controller
public class PictureController  {

    @Value("${IMAGE_SERVER_URL}")
    private String urlPrefix;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map picUpload(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String url = null;
        Map result = new HashMap();
        try {
            FastDFSClient client = new FastDFSClient("classpath:resource/client.conf");
            //group1/M00/00/00/wKhkZFt9bz6AYzQcAACMhKNNYso661.jpg
            url = urlPrefix+client.uploadFile(multipartFile.getBytes(), suffix);
            System.out.print("url:"+url);
            result.put("error",0);
            result.put("url",url);
            result.put("message","upload success ");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error",1);
            result.put("url","");
            result.put("message","upload fail ");
        }
        return result;
    }
}

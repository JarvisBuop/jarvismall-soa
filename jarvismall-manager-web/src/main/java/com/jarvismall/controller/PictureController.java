package com.jarvismall.controller;

import com.jarvismall.utils.FastDFSClient;
import com.jarvismall.utils.JsonUtils;
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
    public String picUpload(MultipartFile uploadFile){
        String originalFilename = uploadFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String url = null;
        Map result = new HashMap();
        try {
            FastDFSClient client = new FastDFSClient("classpath:resource/client.conf");
            //group1/M00/00/00/wKhkZFt9bz6AYzQcAACMhKNNYso661.jpg
            url = urlPrefix+client.uploadFile(uploadFile.getBytes(), suffix);
            System.out.print("upload:::"+url);
            result.put("error",0);
            result.put("url",url);
            result.put("message","upload success ");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error",1);
            result.put("url","");
            result.put("message","upload fail ");
        }
        return JsonUtils.objectToJson(result);
    }
}

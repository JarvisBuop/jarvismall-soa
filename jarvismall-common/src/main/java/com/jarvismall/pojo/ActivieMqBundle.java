package com.jarvismall.pojo;

import com.jarvismall.utils.JsonUtils;

import java.io.Serializable;

/**
 * Created by JarvisDong on 2018/9/14.
 */
public class ActivieMqBundle implements Serializable{
    private String id;
    private String type;

    public ActivieMqBundle(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public ActivieMqBundle() {
    }

    public static String createJson(ActivieMqBundle bundle){
        return JsonUtils.objectToJson(bundle);
    }

    public static ActivieMqBundle createBundle(String mqBundle){
        return JsonUtils.jsonToPojo(mqBundle,ActivieMqBundle.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

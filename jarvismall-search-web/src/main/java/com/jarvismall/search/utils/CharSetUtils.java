package com.jarvismall.search.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by JarvisDong on 2018/9/8.
 */
public class CharSetUtils {

    public static String convert(String targetStr){
        try {
            return new String(targetStr.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return targetStr;
    }
}

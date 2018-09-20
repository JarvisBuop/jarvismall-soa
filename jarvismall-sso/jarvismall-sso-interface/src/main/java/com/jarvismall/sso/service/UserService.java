package com.jarvismall.sso.service;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbUser;

/**
 * Created by JarvisDong on 2018/9/17.
 */
public interface UserService {

    TaotaoResult checkUser(String param,int type);

    TaotaoResult registerUser(TbUser tbUser);

    TaotaoResult login(String username,String password);

    TaotaoResult getUserInfoByToken(String token);

    TaotaoResult safeExit(String token);
}

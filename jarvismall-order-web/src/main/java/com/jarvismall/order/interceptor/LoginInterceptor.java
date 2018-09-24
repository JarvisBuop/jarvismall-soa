package com.jarvismall.order.interceptor;

import com.jarvismall.pojo.TaotaoResult;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.pojo.TbUser;
import com.jarvismall.sso.service.UserService;
import com.jarvismall.utils.CookieUtils;
import com.jarvismall.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by JarvisDong on 2018/9/24.
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${COOKIE_KEY}")
    String COOKIE_KEY;
    @Value("${URL_REDIRECT}")
    String URL_REDIRECT;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //取token;
        String token = CookieUtils.getCookieValue(httpServletRequest, COOKIE_KEY, true);
        StringBuffer requestURL = httpServletRequest.getRequestURL();
        if (StringUtils.isBlank(token)) {
            //当前请求的url;
            httpServletResponse.sendRedirect(URL_REDIRECT + "/page/login" + "?url=" + requestURL.toString());
            return false;//拦截
        }
        //根据cookie中的token判断是否登录;
        TaotaoResult userInfoByToken = userService.getUserInfoByToken(token);

        if (userInfoByToken.getStatus() != 200) {
            //当前请求的url;
            httpServletResponse.sendRedirect(URL_REDIRECT + "/page/login" + "?url=" + requestURL.toString());
            return false;//拦截
        }
        httpServletRequest.setAttribute("user", userInfoByToken.getData());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.jarvismall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by JarvisDong on 2018/9/11.
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger mLogger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object handler, Exception e) {
        mLogger.info("进入全局异常处理器..");
        mLogger.debug("测试handler的类型: "+handler.getClass());
        //打印异常
        e.printStackTrace();
        //日志文件写入异常
        mLogger.error("系统发生异常",e);
        //发邮件,短信

        //展示错误页面;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message","出现异常了,请稍后重试~");
        modelAndView.setViewName("/error/exception");
        return modelAndView;
    }
}

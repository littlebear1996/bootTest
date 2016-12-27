/**
 * Company
 * Copyright (C) 2004-2016 All Rights Reserved.
 */
package com.chinaredstar.interceptor;

import com.chinaredstar.api.vo.User;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器实现对于所有未登录用户进行拦截调至登录页面
 * @author Littlebear1996
 * @version $Id LoginCheckInterceptor.java, v 0.1 2016-12-26 下午5:05 Littlebear1996 Exp $$
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Object cookies = httpServletRequest.getCookies();
        System.out.println(cookies);
        Object user = httpServletRequest.getSession().getAttribute("userName");
        if(user==null||!(user instanceof String)){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
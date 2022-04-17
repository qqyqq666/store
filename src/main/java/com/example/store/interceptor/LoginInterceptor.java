package com.example.store.interceptor;

/**
 * @author JlX
 * @create 2022-04-09 10:33
 */

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义处理器拦截器 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    /**
     * 检测全局session对象中是否有uid数据，如果有则放行，如果没有重定向到登录页面
     */
    //在调用所有处理请求的方法之前被自动调用执行的方法

    /**
     *request:请求对象
     * response ：响应对象
     * handler :处理器（url+controller ：映射）
     * return：如果返回值为true表示放行当前的请求，如果返回值false则表示拦截当前的请求
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("uid") == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}


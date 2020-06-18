package com.example.shiro.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: Liu
 * @Date: 2020/6/15 09:19
 */
public class MyShiroFilter extends FormAuthenticationFilter {

    /**
     * 对没有登录的post请求返回json
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        if (Constant.POST.equals(((HttpServletRequest) request).getMethod())) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject resMsg = new JSONObject();
            resMsg.put("msg", "您尚未登录，请登录!");
            resMsg.put("data", "您尚未登录，请登录!");
            resMsg.put("code", 401);
            response.getWriter().print(resMsg.toString());
        } else {
            servletResponse.sendRedirect("/login");
        }
    }
}

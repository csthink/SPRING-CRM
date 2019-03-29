package com.csthink.crm.global.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();

        if (path.toLowerCase().contains("login")) { // 登录相关页面
            filterChain.doFilter(request, response);
        } else {
            if (null != request.getSession().getAttribute("USER")) { // 用户已登录
                filterChain.doFilter(request, response);
            } else { // 用户未登录
                response.sendRedirect("/login/toLogin.do");
            }
        }
    }

    @Override
    public void destroy() {

    }
}

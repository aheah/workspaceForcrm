package com.ly.crm.web.filter;

import com.ly.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到验证有没有登录过的过滤器");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String path = request.getServletPath();
        //不应该被拦截的资源，自动放行
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //如果不为null，说明登陆过
            if (user != null){
                filterChain.doFilter(servletRequest,servletResponse);
                //没有登陆过
            }else {

                //重定向到登录页
                /*
                 * 转发:用的是一种有特殊的绝对路径的写法，绝对路劲前不加/项目名，这种路径也称为内部路径/login.jsp
                 *
                 * 重定向：
                 *   使用的是传统绝对路径的写法，前面必须以/项目名开头，后面根具体的资源路径/crm/login.jsp
                 * 为什么不用转发？
                 * 转发后，路径会停留在老路径上，而不是跳转之后的最新资源路径
                 * 我们应该在为用户跳转到登录页的同时，将浏览器的地址栏应该自动设置为当前的登录页的路径
                 * */

                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

package com.ly.crm.settings.web.controller;

import com.ly.crm.settings.domain.User;
import com.ly.crm.settings.service.UserService;
import com.ly.crm.settings.service.impl.UserServiceImpl;
import com.ly.crm.utils.MD5Util;
import com.ly.crm.utils.PrintJson;
import com.ly.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path = request.getServletPath();
        System.out.println(path);
        if ("/settings/user/login.do".equals(path)){
            System.out.println("11111");
            login(request,response);
        }else if ("".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入验证登录操作");
        //获得密码并用密文形式传递（数据库中是密文形式）
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);
        //接受浏览器端的ip地址
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        //未来业务层开发，统一用代理类形态的接口对象
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            //到这里已经从三层架构回来了，准备到响应前端的ajax去
            User user = userService.login(loginAct,loginPwd,ip);
            //如果执行到此，说明了业务层没有为controller抛出任何异常
            request.getSession().setAttribute("user",user);
            /*
            * {"success":"true"}
            * */
            PrintJson.printJsonFlag(response,true);
            }catch (Exception e){
                //一旦执行到此，说明业务层为我们验证失败，为controller抛出异常
                //表示登陆失败
                /*
                * {"success":false,"msg":?}
                *
                * */
                e.printStackTrace();
                String msg = e.getMessage();
                //此时要为ajax请求提供更多信息，封装或用map
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("success",false);
                map.put("msg",msg);
                //响应
                PrintJson.printJsonObj(response,map);
        }
    }
}

package com.ly.crm.utils;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionInvocationHandler implements InvocationHandler {
    //目标类
    private Object target;

    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SqlSession session = null;
        Object obj = null;
        try{
            session = SqlSessionUtil.getSession();
            //处理业务逻辑
            //调用目标类方法
            obj = method.invoke(target,args);
            //处理业务逻辑完毕，提交事务
            session.commit();
        }catch (Exception e){
            session.rollback();
            e.printStackTrace();

            //处理的是什么异常，继续往上抛说明异常
            throw e.getCause();
        }finally {
            SqlSessionUtil.myClose(session);
        }
        return obj;
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
}

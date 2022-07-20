package com.ly.crm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//工具类
public class SqlSessionUtil {
    private SqlSessionUtil(){}
    private static SqlSessionFactory sqlSessionFactory = null;
    static{
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
    //取得SqlSession对象
    public static SqlSession getSession(){
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession == null){
            sqlSession =sqlSessionFactory.openSession();
            threadLocal.set(sqlSession);
        }
        return sqlSession;
    }
    //关闭SqlSession对象
    static void myClose(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.close();
            //这句必须加，非常容易忘记
            threadLocal.remove();
        }
    }
}

package com.ly.workbench.test;

import com.ly.crm.utils.ServiceFactory;
import com.ly.crm.utils.UUIDUtil;
import com.ly.crm.workbench.domain.Activity;
import com.ly.crm.workbench.service.ActivityService;
import com.ly.crm.workbench.service.impl.ActivityServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {
    @Test
    public void testSave(){
        Activity a = new Activity();
        a.setId(UUIDUtil.getUUID());
        a.setName("宣传推广会");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.save(a);
        System.out.println(flag);
        //与预测结果一致则成功否则失败
        Assert.assertEquals(flag,true);
    }
}

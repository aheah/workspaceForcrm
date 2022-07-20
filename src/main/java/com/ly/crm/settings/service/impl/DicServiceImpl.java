package com.ly.crm.settings.service.impl;

import com.ly.crm.settings.dao.DicTypeDao;
import com.ly.crm.settings.dao.DicValueDao;
import com.ly.crm.settings.domain.DicType;
import com.ly.crm.settings.domain.DicValue;
import com.ly.crm.settings.service.DicService;
import com.ly.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map = new HashMap<String,List<DicValue>>();
        //将字典类型列表取出
        List<DicType> dlist = dicTypeDao.getTypeList();

        //将字典类型列表遍历
        for (DicType dt:dlist){

            //取得每一种类型的字典类型编码
            String code = dt.getCode();
            //根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code + "List",dvList);
        }
        return map;
    }
}

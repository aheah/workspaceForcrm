package com.ly.crm.workbench.service.impl;

import com.ly.crm.utils.ServiceFactory;
import com.ly.crm.utils.SqlSessionUtil;
import com.ly.crm.workbench.dao.CustomerDao;
import com.ly.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList = customerDao.getCustomerName(name);
        return sList;
    }
}

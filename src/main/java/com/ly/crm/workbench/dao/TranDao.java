package com.ly.crm.workbench.dao;

import com.ly.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    Tran detail(String id);

    int save(Tran t);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getCharts();
}

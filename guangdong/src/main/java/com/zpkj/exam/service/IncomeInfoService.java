package com.zpkj.exam.service;

import com.github.pagehelper.Page;
import com.zpkj.exam.domain.IncomeInfo;
import com.zpkj.exam.domain.form.IncomeInfoForm;
import com.zpkj.exam.domain.query.IncomeInfoQuery;

import java.util.List;

/**
 * IncomeInfoService 
 */
public interface IncomeInfoService {

    /**
     * 查询 IncomeInfo
     */
    List<IncomeInfo> find(final IncomeInfoQuery query);
    
    /**
     * 分页查询 IncomeInfo
     */
    Page<IncomeInfo> findByPage(final IncomeInfoQuery query);

    /**
     * 通过id得到一个 IncomeInfo
     */
    IncomeInfo get(final String id);
    
    /**
     * 新增 IncomeInfo
     */
    void add(final IncomeInfoForm form);

    /**
     * 修改 IncomeInfo
     */
    int update(final IncomeInfoForm form);
    
    /**
     * 删除一个 IncomeInfo
     */
    int delete(final String id);

}
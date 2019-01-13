package com.zpkj.exam.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.dao.IncomeInfoDao;
import com.zpkj.exam.domain.form.IncomeInfoForm;
import com.zpkj.exam.domain.IncomeInfo;
import com.zpkj.exam.domain.query.IncomeInfoQuery;
import com.zpkj.exam.service.IncomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * IncomeInfoService 
 */
@Transactional
@Service
public class IncomeInfoServiceImpl implements IncomeInfoService {
    @Autowired 
    private IncomeInfoDao incomeInfoDao;
	/**
     * 查询 IncomeInfo
     */
    public List<IncomeInfo> find(final IncomeInfoQuery query) {
        return incomeInfoDao.find(query);
    }
    
    /**
     * 分页查询 IncomeInfo
     */
    public Page<IncomeInfo> findByPage(final IncomeInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<IncomeInfo>) incomeInfoDao.find(query);
    }

    /**
     * 通过id得到一个 IncomeInfo
     */
    public IncomeInfo get(final String id) {
        return incomeInfoDao.get(id);
    }
    
    /**
     * 新增 IncomeInfo
     */
    public void add(final IncomeInfoForm form) {
        incomeInfoDao.add(form);
    }

    /**
     * 修改 IncomeInfo
     */
    public int update(final IncomeInfoForm form) {
        return incomeInfoDao.update(form);
    }
    
    /**
     * 删除一个 IncomeInfo
     */
    public int delete(final String id) {
        return incomeInfoDao.delete(id);
    }
	
}
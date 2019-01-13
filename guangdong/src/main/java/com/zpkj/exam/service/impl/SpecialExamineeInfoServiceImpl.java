package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.SpecialExamineeInfo;
import com.zpkj.exam.domain.form.SpecialExamineeInfoForm;
import com.zpkj.exam.domain.query.SpecialExamineeInfoQuery;
import com.zpkj.exam.dao.SpecialExamineeInfoDao;
import com.zpkj.exam.service.SpecialExamineeInfoService;


/**
 * SpecialExamineeInfoService 
 */
@Service
public class SpecialExamineeInfoServiceImpl implements SpecialExamineeInfoService {
    @Autowired 
    private SpecialExamineeInfoDao specialExamineeInfoDao;
	
	/**
     * 查询 SpecialExamineeInfo
     */
    public List<SpecialExamineeInfo> find(final SpecialExamineeInfoQuery query) {
        return specialExamineeInfoDao.find(query);
    }
    
    /**
     * 分页查询 SpecialExamineeInfo
     */
    public Page<SpecialExamineeInfo> findByPage(final SpecialExamineeInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<SpecialExamineeInfo>) specialExamineeInfoDao.find(query);
    }

    /**
     * 通过id得到一个 SpecialExamineeInfo
     */
    public SpecialExamineeInfo get(final String id) {
        return specialExamineeInfoDao.get(id);
    }
    
    /**
     * 新增 SpecialExamineeInfo
     */
    public void add(final SpecialExamineeInfoForm form) {
        specialExamineeInfoDao.add(form);
    }

    /**
     * 修改 SpecialExamineeInfo
     */
    public int update(final SpecialExamineeInfoForm form) {
        return specialExamineeInfoDao.update(form);
    }
    
    /**
     * 删除一个 SpecialExamineeInfo
     */
    public int delete(final String id) {
        return specialExamineeInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return specialExamineeInfoDao.changeStatus(id, status);
    }
	
}
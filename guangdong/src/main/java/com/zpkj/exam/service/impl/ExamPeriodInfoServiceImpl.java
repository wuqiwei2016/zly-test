package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.ExamPeriodInfo;
import com.zpkj.exam.domain.form.ExamPeriodInfoForm;
import com.zpkj.exam.domain.query.ExamPeriodInfoQuery;
import com.zpkj.exam.dao.ExamPeriodInfoDao;
import com.zpkj.exam.service.ExamPeriodInfoService;


/**
 * ExamPeriodInfoService 
 */
@Service
public class ExamPeriodInfoServiceImpl implements ExamPeriodInfoService {
    @Autowired 
    private ExamPeriodInfoDao examPeriodInfoDao;
	
	/**
     * 查询 ExamPeriodInfo
     */
    public List<ExamPeriodInfo> find(final ExamPeriodInfoQuery query) {
        return examPeriodInfoDao.find(query);
    }
    
    /**
     * 分页查询 ExamPeriodInfo
     */
    public Page<ExamPeriodInfo> findByPage(final ExamPeriodInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamPeriodInfo>) examPeriodInfoDao.find(query);
    }

    /**
     * 通过id得到一个 ExamPeriodInfo
     */
    public ExamPeriodInfo get(final String id) {
        return examPeriodInfoDao.get(id);
    }
    
    /**
     * 新增 ExamPeriodInfo
     */
    public void add(final ExamPeriodInfoForm form) {
        examPeriodInfoDao.add(form);
    }

    /**
     * 修改 ExamPeriodInfo
     */
    public int update(final ExamPeriodInfoForm form) {
        return examPeriodInfoDao.update(form);
    }
    
    /**
     * 删除一个 ExamPeriodInfo
     */
    public int delete(final String id) {
        return examPeriodInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return examPeriodInfoDao.changeStatus(id, status);
    }
	
}
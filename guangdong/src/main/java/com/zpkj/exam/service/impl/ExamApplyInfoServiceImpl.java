package com.zpkj.exam.service.impl;

import com.zpkj.exam.domain.ExamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.form.ExamApplyInfoForm;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;
import com.zpkj.exam.dao.ExamApplyInfoDao;
import com.zpkj.exam.service.ExamApplyInfoService;


/**
 * ExamApplyInfoService 
 */
@Service
public class ExamApplyInfoServiceImpl implements ExamApplyInfoService {
    @Autowired 
    private ExamApplyInfoDao examApplyInfoDao;
	
	/**
     * 查询 ExamApplyInfo
     */
    public Page<ExamApplyInfo> find(final ExamApplyInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamApplyInfo>)examApplyInfoDao.find(query);
    }
    
    /**
     * 分页查询 ExamApplyInfo
     */
    public Page<ExamApplyInfo> findByPage(final ExamApplyInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamApplyInfo>) examApplyInfoDao.find(query);
    }

    /**
     * 通过id得到一个 ExamApplyInfo
     */
    public ExamApplyInfo get(final String id) {
        return examApplyInfoDao.get(id);
    }
    
    /**
     * 新增 ExamApplyInfo
     */
    public void add(final ExamApplyInfoForm form) {
        examApplyInfoDao.add(form);
    }

    /**
     * 修改 ExamApplyInfo
     */
    public int update(final ExamApplyInfoForm form) {
        return examApplyInfoDao.update(form);
    }
    
    /**
     * 删除一个 ExamApplyInfo
     */
    public int delete(final String id) {
        return examApplyInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return examApplyInfoDao.changeStatus(id, status);
    }
	
}
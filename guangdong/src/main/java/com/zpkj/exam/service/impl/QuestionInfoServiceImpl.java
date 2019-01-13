package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.QuestionInfo;
import com.zpkj.exam.domain.form.QuestionInfoForm;
import com.zpkj.exam.domain.query.QuestionInfoQuery;
import com.zpkj.exam.dao.QuestionInfoDao;
import com.zpkj.exam.service.QuestionInfoService;


/**
 * QuestionInfoService 
 */
@Service
public class QuestionInfoServiceImpl implements QuestionInfoService {
    @Autowired 
    private QuestionInfoDao questionInfoDao;
	
	/**
     * 查询 QuestionInfo
     */
    public List<QuestionInfo> find(final QuestionInfoQuery query) {
        return questionInfoDao.find(query);
    }
    
    /**
     * 分页查询 QuestionInfo
     */
    public Page<QuestionInfo> findByPage(final QuestionInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<QuestionInfo>) questionInfoDao.find(query);
    }

    /**
     * 通过id得到一个 QuestionInfo
     */
    public QuestionInfo get(final String id) {
        return questionInfoDao.get(id);
    }
    
    /**
     * 新增 QuestionInfo
     */
    public void add(final QuestionInfoForm form) {
        questionInfoDao.add(form);
    }

    /**
     * 修改 QuestionInfo
     */
    public int update(final QuestionInfoForm form) {
        return questionInfoDao.update(form);
    }
    
    /**
     * 删除一个 QuestionInfo
     */
    public int delete(final String id) {
        return questionInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return questionInfoDao.changeStatus(id, status);
    }
	
}
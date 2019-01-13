package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.QuestionJudgeInfo;
import com.zpkj.exam.domain.form.QuestionJudgeInfoForm;
import com.zpkj.exam.domain.query.QuestionJudgeInfoQuery;
import com.zpkj.exam.dao.QuestionJudgeInfoDao;
import com.zpkj.exam.service.QuestionJudgeInfoService;


/**
 * QuestionJudgeInfoService 
 */
@Service
public class QuestionJudgeInfoServiceImpl implements QuestionJudgeInfoService {
    @Autowired 
    private QuestionJudgeInfoDao questionJudgeInfoDao;
	
	/**
     * 查询 QuestionJudgeInfo
     */
    public List<QuestionJudgeInfo> find(final QuestionJudgeInfoQuery query) {
        return questionJudgeInfoDao.find(query);
    }

    /**
     * 查询 QuestionJudgeInfo
     */
    public List<QuestionJudgeInfo> findByLimit(final QuestionJudgeInfoQuery query) {
        return questionJudgeInfoDao.findByLimit(query);
    }

    /**
     * 分页查询 QuestionJudgeInfo
     */
    public Page<QuestionJudgeInfo> findByPage(final QuestionJudgeInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<QuestionJudgeInfo>) questionJudgeInfoDao.find(query);
    }

    /**
     * 通过id得到一个 QuestionJudgeInfo
     */
    public QuestionJudgeInfo get(final String id) {
        return questionJudgeInfoDao.get(id);
    }
    
    /**
     * 新增 QuestionJudgeInfo
     */
    public void add(final QuestionJudgeInfoForm form) {
        questionJudgeInfoDao.add(form);
    }

    /**
     * 修改 QuestionJudgeInfo
     */
    public int update(final QuestionJudgeInfoForm form) {
        return questionJudgeInfoDao.update(form);
    }
    
    /**
     * 删除一个 QuestionJudgeInfo
     */
    public int delete(final String id) {
        return questionJudgeInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return questionJudgeInfoDao.changeStatus(id, status);
    }

    /**
     * 随机得到一个 QuestionMultipleInfo
     */
    public QuestionJudgeInfo getRandom(QuestionJudgeInfoQuery query) {
        return questionJudgeInfoDao.getRandom(query);
    }
	
}
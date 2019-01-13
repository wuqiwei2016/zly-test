package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.QuestionMultipleInfo;
import com.zpkj.exam.domain.form.QuestionMultipleInfoForm;
import com.zpkj.exam.domain.query.QuestionMultipleInfoQuery;
import com.zpkj.exam.dao.QuestionMultipleInfoDao;
import com.zpkj.exam.service.QuestionMultipleInfoService;


/**
 * QuestionMultipleInfoService 
 */
@Service
public class QuestionMultipleInfoServiceImpl implements QuestionMultipleInfoService {
    @Autowired 
    private QuestionMultipleInfoDao questionMultipleInfoDao;
	
	/**
     * 查询 QuestionMultipleInfo
     */
    public List<QuestionMultipleInfo> find(final QuestionMultipleInfoQuery query) {
        return questionMultipleInfoDao.find(query);
    }

    /**
     * 查询 QuestionMultipleInfo
     */
    public List<QuestionMultipleInfo> findByLimit(final QuestionMultipleInfoQuery query) {
        return questionMultipleInfoDao.findByLimit(query);
    }
    
    /**
     * 分页查询 QuestionMultipleInfo
     */
    public Page<QuestionMultipleInfo> findByPage(final QuestionMultipleInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<QuestionMultipleInfo>) questionMultipleInfoDao.find(query);
    }

    /**
     * 通过id得到一个 QuestionMultipleInfo
     */
    public QuestionMultipleInfo get(final String id) {
        return questionMultipleInfoDao.get(id);
    }
    
    /**
     * 新增 QuestionMultipleInfo
     */
    public void add(final QuestionMultipleInfoForm form) {
        questionMultipleInfoDao.add(form);
    }

    /**
     * 修改 QuestionMultipleInfo
     */
    public int update(final QuestionMultipleInfoForm form) {
        return questionMultipleInfoDao.update(form);
    }
    
    /**
     * 删除一个 QuestionMultipleInfo
     */
    public int delete(final String id) {
        return questionMultipleInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return questionMultipleInfoDao.changeStatus(id, status);
    }

    /**
     * 随机得到一个 QuestionMultipleInfo
     */
    public QuestionMultipleInfo getRandom(QuestionMultipleInfoQuery query) {
        return questionMultipleInfoDao.getRandom(query);
    }
	
}
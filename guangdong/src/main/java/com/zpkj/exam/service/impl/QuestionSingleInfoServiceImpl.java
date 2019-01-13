package com.zpkj.exam.service.impl;

import com.zpkj.exam.domain.ExamItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.QuestionSingleInfo;
import com.zpkj.exam.domain.form.QuestionSingleInfoForm;
import com.zpkj.exam.domain.query.QuestionSingleInfoQuery;
import com.zpkj.exam.dao.QuestionSingleInfoDao;
import com.zpkj.exam.service.QuestionSingleInfoService;


/**
 * QuestionSingleInfoService
 */
@Service
public class QuestionSingleInfoServiceImpl implements QuestionSingleInfoService {
    @Autowired
    private QuestionSingleInfoDao questionSingleInfoDao;

    /**
     * 查询 QuestionSingleInfo
     */
    public List<QuestionSingleInfo> find(final QuestionSingleInfoQuery query) {
        return questionSingleInfoDao.find(query);
    }

    /**
     * 查询 QuestionSingleInfo
     */
    public List<QuestionSingleInfo> findByLimit(final QuestionSingleInfoQuery query) {
        return questionSingleInfoDao.findByLimit(query);
    }

    /**
     * 分页查询 QuestionSingleInfo
     */
    public Page<QuestionSingleInfo> findByPage(final QuestionSingleInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<QuestionSingleInfo>) questionSingleInfoDao.find(query);
    }

    /**
     * 通过id得到一个 QuestionSingleInfo
     */
    public QuestionSingleInfo get(final String id) {
        return questionSingleInfoDao.get(id);
    }

    /**
     * 新增 QuestionSingleInfo
     */
    public void add(final QuestionSingleInfoForm form) {
        questionSingleInfoDao.add(form);
    }

    /**
     * 修改 QuestionSingleInfo
     */
    public int update(final QuestionSingleInfoForm form) {
        return questionSingleInfoDao.update(form);
    }

    /**
     * 删除一个 QuestionSingleInfo
     */
    public int delete(final String id) {
        return questionSingleInfoDao.delete(id);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return questionSingleInfoDao.changeStatus(id, status);
    }

    @Override
    public List<ExamItemInfo> fingTitleList(QuestionSingleInfoForm questionForm) {
        return questionSingleInfoDao.fingTitleList(questionForm);
    }

    /**
     * 随机得到一个 QuestionSingleInfo
     */
    public QuestionSingleInfo getRandom(QuestionSingleInfoQuery query) {
        return questionSingleInfoDao.getRandom(query);
    }

}
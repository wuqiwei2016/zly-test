package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.ExamItemInfo;
import com.zpkj.exam.domain.form.ExamItemInfoForm;
import com.zpkj.exam.domain.query.ExamItemInfoQuery;
import com.zpkj.exam.dao.ExamItemInfoDao;
import com.zpkj.exam.service.ExamItemInfoService;


/**
 * ExamItemInfoService
 */
@Service
public class ExamItemInfoServiceImpl implements ExamItemInfoService {
    @Autowired
    private ExamItemInfoDao examItemInfoDao;

    /**
     * 查询 ExamItemInfo
     */
    public List<ExamItemInfo> find(final ExamItemInfoQuery query) {
        return examItemInfoDao.find(query);
    }

    /**
     * 分页查询 ExamItemInfo
     */
    public Page<ExamItemInfo> findByPage(final ExamItemInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamItemInfo>) examItemInfoDao.find(query);
    }

    /**
     * 通过id得到一个 ExamItemInfo
     */
    public ExamItemInfo get(final String id) {
        return examItemInfoDao.get(id);
    }

    /**
     * 新增 ExamItemInfo
     */
    public void add(final ExamItemInfoForm form) {
        examItemInfoDao.add(form);
    }

    /**
     * 修改 ExamItemInfo
     */
    public int update(final ExamItemInfoForm form) {
        return examItemInfoDao.update(form);
    }

    /**
     * 删除一个 ExamItemInfo
     */
    public int delete(final String id) {
        return examItemInfoDao.delete(id);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return examItemInfoDao.changeStatus(id, status);
    }

    /**
     * 修改答案
     */
    public int changeAnswer(final String id, final String answer) {
        return examItemInfoDao.changeAnswer(id, answer);
    }

    /**
     * 通过examId, orderNum得到一个 ExamItemInfo
     */
    public ExamItemInfo getByExamIdAndOrderNum(final String examId, final Integer orderNum) {
        return examItemInfoDao.getByExamIdAndOrderNum(examId, orderNum);
    }
}
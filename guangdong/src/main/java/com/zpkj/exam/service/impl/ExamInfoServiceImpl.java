package com.zpkj.exam.service.impl;

import com.zpkj.exam.dao.ExamApplyInfoDao;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.form.ExamApplyInfoForm;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.form.ExamInfoForm;
import com.zpkj.exam.domain.query.ExamInfoQuery;
import com.zpkj.exam.dao.ExamInfoDao;
import com.zpkj.exam.service.ExamInfoService;


/**
 * ExamInfoService
 */
@Service
public class ExamInfoServiceImpl implements ExamInfoService {
    @Autowired
    private ExamInfoDao examInfoDao;
    @Autowired
    private ExamApplyInfoDao examApplyInfoDao;

    /**
     * 查询 ExamInfo
     */
    public List<ExamInfo> find(final ExamInfoQuery query) {
        return examInfoDao.find(query);
    }

    /**
     * 分页查询 ExamInfo
     */
    public Page<ExamInfo> findByPage(final ExamInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamInfo>) examInfoDao.find(query);
    }

    /**
     * 分页查询 ExamInfo
     */
    public Page<ExamInfo> findDistinctByPage(final ExamInfoQuery query){
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamInfo>) examInfoDao.findDistinctByPage(query);
    }

    /**
     * 通过id得到一个 ExamInfo
     */
    public ExamInfo get(final String id) {
        return examInfoDao.get(id);
    }

    /**
     * 新增 ExamInfo
     */
    public void add(final ExamInfoForm form) {
        examInfoDao.add(form);
    }

    /**
     * 修改 ExamInfo
     */
    public int update(final ExamInfoForm form) {
        return examInfoDao.update(form);
    }

    /**
     * 删除一个 ExamInfo
     */
    public int delete(final String id) {
        return examInfoDao.delete(id);
    }

    /**
     * 删除 ExamInfo
     */
    public int deleteByUserId(final String examPeriodId, final String userId){
        return examInfoDao.deleteByUserId(examPeriodId, userId);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return examInfoDao.changeStatus(id, status);
    }

    /**
     * 修改通知状态
     */
    public int changeIsInform(final String id, final Integer isInform) {
        return examInfoDao.changeIsInform(id, isInform);
    }

    @Override
    public void upExamStatusByUidOrSt(ExamInfo examInfo) {
        examInfoDao.upExamStatusByUidOrSt(examInfo);
    }

    @Override
    public Page<ExamInfo> findByPageTwo(ExamInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ExamInfo>) examInfoDao.findTwo(query);
    }

    @Override
    public List<ExamInfo> findByUserId(String userId) {
        return examInfoDao.findByUserId(userId);
    }

    @Override
    public int findExamListByexamPeriodId(String id) {
        return examInfoDao.findExamListByexamPeriodId(id);
    }

}
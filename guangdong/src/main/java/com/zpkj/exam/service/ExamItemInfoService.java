package com.zpkj.exam.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zpkj.exam.domain.ExamItemInfo;
import com.zpkj.exam.domain.form.ExamItemInfoForm;
import com.zpkj.exam.domain.query.ExamItemInfoQuery;

/**
 * ExamItemInfoService
 */
public interface ExamItemInfoService {

    /**
     * 查询 ExamItemInfo
     */
    List<ExamItemInfo> find(final ExamItemInfoQuery query);

    /**
     * 分页查询 ExamItemInfo
     */
    Page<ExamItemInfo> findByPage(final ExamItemInfoQuery query);

    /**
     * 通过id得到一个 ExamItemInfo
     */
    ExamItemInfo get(final String id);

    /**
     * 新增 ExamItemInfo
     */
    void add(final ExamItemInfoForm form);

    /**
     * 修改 ExamItemInfo
     */
    int update(final ExamItemInfoForm form);

    /**
     * 删除一个 ExamItemInfo
     */
    int delete(final String id);

    /**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

    /**
     * 修改答案
     */
    int changeAnswer(final String id, final String answer);

    /**
     * 通过examId, orderNum得到一个 ExamItemInfo
     */
    ExamItemInfo getByExamIdAndOrderNum(final String examId, final Integer orderNum);

}
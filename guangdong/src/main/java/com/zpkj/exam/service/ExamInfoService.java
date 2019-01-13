package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.form.ExamInfoForm;
import com.zpkj.exam.domain.query.ExamInfoQuery;

/**
 * ExamInfoService 
 */
public interface ExamInfoService {

    /**
     * 查询 ExamInfo
     */
    List<ExamInfo> find(final ExamInfoQuery query);
    
    /**
     * 分页查询 ExamInfo
     */
    Page<ExamInfo> findByPage(final ExamInfoQuery query);

    /**
     * 分页查询 ExamInfo
     */
    Page<ExamInfo> findDistinctByPage(final ExamInfoQuery query);

    /**
     * 通过id得到一个 ExamInfo
     */
    ExamInfo get(final String id);
    
    /**
     * 新增 ExamInfo
     */
    void add(final ExamInfoForm form);

    /**
     * 修改 ExamInfo
     */
    int update(final ExamInfoForm form);
    
    /**
     * 删除一个 ExamInfo
     */
    int delete(final String id);

    /**
     * 删除 ExamInfo
     */
    int deleteByUserId(final String examPeriodId, final String userId);

	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

    /**
     * 修改通知状态
     */
    int changeIsInform(final String id, final Integer isInform);

    void upExamStatusByUidOrSt(ExamInfo examInfo);

    Page<ExamInfo> findByPageTwo(ExamInfoQuery query);

    List<ExamInfo> findByUserId(String userId);

    int findExamListByexamPeriodId(String id);
}
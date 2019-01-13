package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.ExamPeriodInfo;
import com.zpkj.exam.domain.form.ExamPeriodInfoForm;
import com.zpkj.exam.domain.query.ExamPeriodInfoQuery;

/**
 * ExamPeriodInfoService 
 */
public interface ExamPeriodInfoService {

    /**
     * 查询 ExamPeriodInfo
     */
    List<ExamPeriodInfo> find(final ExamPeriodInfoQuery query);
    
    /**
     * 分页查询 ExamPeriodInfo
     */
    Page<ExamPeriodInfo> findByPage(final ExamPeriodInfoQuery query);

    /**
     * 通过id得到一个 ExamPeriodInfo
     */
    ExamPeriodInfo get(final String id);
    
    /**
     * 新增 ExamPeriodInfo
     */
    void add(final ExamPeriodInfoForm form);

    /**
     * 修改 ExamPeriodInfo
     */
    int update(final ExamPeriodInfoForm form);
    
    /**
     * 删除一个 ExamPeriodInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

}
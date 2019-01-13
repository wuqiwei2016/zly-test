package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.form.ExamApplyInfoForm;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;

/**
 * ExamApplyInfoService 
 */
public interface ExamApplyInfoService {

    /**
     * 查询 ExamApplyInfo
     */
    Page<ExamApplyInfo> find(final ExamApplyInfoQuery query);
    
    /**
     * 分页查询 ExamApplyInfo
     */
    Page<ExamApplyInfo> findByPage(final ExamApplyInfoQuery query);

    /**
     * 通过id得到一个 ExamApplyInfo
     */
    ExamApplyInfo get(final String id);
    
    /**
     * 新增 ExamApplyInfo
     */
    void add(final ExamApplyInfoForm form);

    /**
     * 修改 ExamApplyInfo
     */
    int update(final ExamApplyInfoForm form);
    
    /**
     * 删除一个 ExamApplyInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

}
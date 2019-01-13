package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.QuestionJudgeInfo;
import com.zpkj.exam.domain.form.QuestionJudgeInfoForm;
import com.zpkj.exam.domain.query.QuestionJudgeInfoQuery;

/**
 * QuestionJudgeInfoService 
 */
public interface QuestionJudgeInfoService {

    /**
     * 查询 QuestionJudgeInfo
     */
    List<QuestionJudgeInfo> find(final QuestionJudgeInfoQuery query);

    /**
     * 查询 QuestionJudgeInfo
     */
    List<QuestionJudgeInfo> findByLimit(final QuestionJudgeInfoQuery query);

    /**
     * 分页查询 QuestionJudgeInfo
     */
    Page<QuestionJudgeInfo> findByPage(final QuestionJudgeInfoQuery query);

    /**
     * 通过id得到一个 QuestionJudgeInfo
     */
    QuestionJudgeInfo get(final String id);
    
    /**
     * 新增 QuestionJudgeInfo
     */
    void add(final QuestionJudgeInfoForm form);

    /**
     * 修改 QuestionJudgeInfo
     */
    int update(final QuestionJudgeInfoForm form);
    
    /**
     * 删除一个 QuestionJudgeInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

    /**
     * 随机得到一个 QuestionJudgeInfo
     */
    QuestionJudgeInfo getRandom(QuestionJudgeInfoQuery query);

}
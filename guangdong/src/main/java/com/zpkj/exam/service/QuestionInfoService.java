package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.QuestionInfo;
import com.zpkj.exam.domain.form.QuestionInfoForm;
import com.zpkj.exam.domain.query.QuestionInfoQuery;

/**
 * QuestionInfoService 
 */
public interface QuestionInfoService {

    /**
     * 查询 QuestionInfo
     */
    List<QuestionInfo> find(final QuestionInfoQuery query);
    
    /**
     * 分页查询 QuestionInfo
     */
    Page<QuestionInfo> findByPage(final QuestionInfoQuery query);

    /**
     * 通过id得到一个 QuestionInfo
     */
    QuestionInfo get(final String id);
    
    /**
     * 新增 QuestionInfo
     */
    void add(final QuestionInfoForm form);

    /**
     * 修改 QuestionInfo
     */
    int update(final QuestionInfoForm form);
    
    /**
     * 删除一个 QuestionInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

}
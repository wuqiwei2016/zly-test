package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.ExamItemInfo;
import com.zpkj.exam.domain.QuestionSingleInfo;
import com.zpkj.exam.domain.form.QuestionSingleInfoForm;
import com.zpkj.exam.domain.query.QuestionSingleInfoQuery;

/**
 * QuestionSingleInfoService 
 */
public interface QuestionSingleInfoService {

    /**
     * 查询 QuestionSingleInfo
     */
    List<QuestionSingleInfo> find(final QuestionSingleInfoQuery query);
    
    /**
     * 查询 QuestionSingleInfo
     */
    List<QuestionSingleInfo> findByLimit(final QuestionSingleInfoQuery query);

    /**
     * 分页查询 QuestionSingleInfo
     */
    Page<QuestionSingleInfo> findByPage(final QuestionSingleInfoQuery query);

    /**
     * 通过id得到一个 QuestionSingleInfo
     */
    QuestionSingleInfo get(final String id);
    
    /**
     * 新增 QuestionSingleInfo
     */
    void add(final QuestionSingleInfoForm form);

    /**
     * 修改 QuestionSingleInfo
     */
    int update(final QuestionSingleInfoForm form);
    
    /**
     * 删除一个 QuestionSingleInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

    List<ExamItemInfo> fingTitleList(QuestionSingleInfoForm questionForm);

    /**
     * 随机得到一个 QuestionSingleInfo
     */
    QuestionSingleInfo getRandom(QuestionSingleInfoQuery query);
}
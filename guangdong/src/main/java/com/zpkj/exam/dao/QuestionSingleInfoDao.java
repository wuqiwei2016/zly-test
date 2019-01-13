package com.zpkj.exam.dao;

import java.util.List;

import com.zpkj.exam.domain.ExamItemInfo;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.QuestionSingleInfo;
import com.zpkj.exam.domain.form.QuestionSingleInfoForm;
import com.zpkj.exam.domain.query.QuestionSingleInfoQuery;

/**
 * QuestionSingleInfoDao 
 */
public interface QuestionSingleInfoDao {
    
    /**
     * 查询 QuestionSingleInfo
     */
    List<QuestionSingleInfo> find(final QuestionSingleInfoQuery query);

    /**
     * 查询 QuestionSingleInfo
     */
    List<QuestionSingleInfo> findByLimit(final QuestionSingleInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    List<ExamItemInfo> fingTitleList(QuestionSingleInfoForm questionForm);

    /**
     * 随机得到一个 QuestionSingleInfo
     */
    QuestionSingleInfo getRandom(QuestionSingleInfoQuery query);
}
package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.QuestionJudgeInfo;
import com.zpkj.exam.domain.form.QuestionJudgeInfoForm;
import com.zpkj.exam.domain.query.QuestionJudgeInfoQuery;

/**
 * QuestionJudgeInfoDao 
 */
public interface QuestionJudgeInfoDao {
    
    /**
     * 查询 QuestionJudgeInfo
     */
    List<QuestionJudgeInfo> find(final QuestionJudgeInfoQuery query);

    /**
     * 查询 QuestionJudgeInfo
     */
    List<QuestionJudgeInfo> findByLimit(final QuestionJudgeInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 随机得到一个 QuestionMultipleInfo
     */
    QuestionJudgeInfo getRandom(QuestionJudgeInfoQuery query);
}
package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.QuestionInfo;
import com.zpkj.exam.domain.form.QuestionInfoForm;
import com.zpkj.exam.domain.query.QuestionInfoQuery;

/**
 * QuestionInfoDao 
 */
public interface QuestionInfoDao {
    
    /**
     * 查询 QuestionInfo
     */
    List<QuestionInfo> find(final QuestionInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}
package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.QuestionMultipleInfo;
import com.zpkj.exam.domain.form.QuestionMultipleInfoForm;
import com.zpkj.exam.domain.query.QuestionMultipleInfoQuery;

/**
 * QuestionMultipleInfoDao 
 */
public interface QuestionMultipleInfoDao {

    /**
     * 查询 QuestionMultipleInfo
     */
    List<QuestionMultipleInfo> find(final QuestionMultipleInfoQuery query);

    /**
     * 查询 QuestionMultipleInfo
     */
    List<QuestionMultipleInfo> findByLimit(final QuestionMultipleInfoQuery query);

    /**
     * 通过id得到一个 QuestionMultipleInfo
     */
    QuestionMultipleInfo get(final String id);
    
    /**
     * 新增 QuestionMultipleInfo
     */
    void add(final QuestionMultipleInfoForm form);

    /**
     * 修改 QuestionMultipleInfo
     */
    int update(final QuestionMultipleInfoForm form);
    
    /**
     * 删除一个 QuestionMultipleInfo
     */
    int delete(final String id);
    
    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 随机得到一个 QuestionMultipleInfo
     */
    QuestionMultipleInfo getRandom(QuestionMultipleInfoQuery query);
}
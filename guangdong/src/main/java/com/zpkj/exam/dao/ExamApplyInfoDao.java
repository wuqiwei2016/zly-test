package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.form.ExamApplyInfoForm;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;

/**
 * ExamApplyInfoDao 
 */
public interface ExamApplyInfoDao {
    
    /**
     * 查询 ExamApplyInfo
     */
    List<ExamApplyInfo> find(final ExamApplyInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}
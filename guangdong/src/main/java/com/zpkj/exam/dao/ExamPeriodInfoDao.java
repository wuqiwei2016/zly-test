package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.ExamPeriodInfo;
import com.zpkj.exam.domain.form.ExamPeriodInfoForm;
import com.zpkj.exam.domain.query.ExamPeriodInfoQuery;

/**
 * ExamPeriodInfoDao 
 */
public interface ExamPeriodInfoDao {
    
    /**
     * 查询 ExamPeriodInfo
     */
    List<ExamPeriodInfo> find(final ExamPeriodInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}
package com.zpkj.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.form.ExamInfoForm;
import com.zpkj.exam.domain.query.ExamInfoQuery;

/**
 * ExamInfoDao
 */
public interface ExamInfoDao {

    /**
     * 查询 ExamInfo
     */
    List<ExamInfo> find(final ExamInfoQuery query);

    /**
     * 查询 ExamInfo
     */
    List<ExamInfo> findDistinctByPage(final ExamInfoQuery query);

    /**
     * 通过id得到一个 ExamInfo
     */
    ExamInfo get(final String id);

    /**
     * 新增 ExamInfo
     */
    void add(final ExamInfoForm form);

    /**
     * 修改 ExamInfo
     */
    int update(final ExamInfoForm form);

    /**
     * 删除一个 ExamInfo
     */
    int delete(final String id);

    /**
     * 删除 ExamInfo
     */
    int deleteByUserId(@Param("examPeriodId") final String examPeriodId, @Param("userId") final String userId);

    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 修改通知状态
     */
    int changeIsInform(@Param("id") final String id, @Param("isInform") final Integer isInform);

    void upExamStatusByUidOrSt(ExamInfo examInfo);

    List<ExamInfo> findTwo(ExamInfoQuery query);

    List<ExamInfo> findByUserId(String userId);

    int findExamListByexamPeriodId(String id);
}
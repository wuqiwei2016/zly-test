package com.zpkj.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.ExamItemInfo;
import com.zpkj.exam.domain.form.ExamItemInfoForm;
import com.zpkj.exam.domain.query.ExamItemInfoQuery;

/**
 * ExamItemInfoDao
 */
public interface ExamItemInfoDao {

    /**
     * 查询 ExamItemInfo
     */
    List<ExamItemInfo> find(final ExamItemInfoQuery query);

    /**
     * 通过id得到一个 ExamItemInfo
     */
    ExamItemInfo get(final String id);

    /**
     * 新增 ExamItemInfo
     */
    void add(final ExamItemInfoForm form);

    /**
     * 修改 ExamItemInfo
     */
    int update(final ExamItemInfoForm form);

    /**
     * 删除一个 ExamItemInfo
     */
    int delete(final String id);

    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 修改答案
     */
    int changeAnswer(@Param("id") final String id, @Param("answer") final String answer);

    /**
     * 通过examId, orderNum得到一个 ExamItemInfo
     */
    ExamItemInfo getByExamIdAndOrderNum(@Param("examId") final String examId, @Param("orderNum") final Integer orderNum);
}
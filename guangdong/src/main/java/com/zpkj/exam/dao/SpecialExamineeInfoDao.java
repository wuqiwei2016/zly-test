package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.SpecialExamineeInfo;
import com.zpkj.exam.domain.form.SpecialExamineeInfoForm;
import com.zpkj.exam.domain.query.SpecialExamineeInfoQuery;

/**
 * SpecialExamineeInfoDao 
 */
public interface SpecialExamineeInfoDao {
    
    /**
     * 查询 SpecialExamineeInfo
     */
    List<SpecialExamineeInfo> find(final SpecialExamineeInfoQuery query);

    /**
     * 通过id得到一个 SpecialExamineeInfo
     */
    SpecialExamineeInfo get(final String id);
    
    /**
     * 新增 SpecialExamineeInfo
     */
    void add(final SpecialExamineeInfoForm form);

    /**
     * 修改 SpecialExamineeInfo
     */
    int update(final SpecialExamineeInfoForm form);
    
    /**
     * 删除一个 SpecialExamineeInfo
     */
    int delete(final String id);
    
    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}
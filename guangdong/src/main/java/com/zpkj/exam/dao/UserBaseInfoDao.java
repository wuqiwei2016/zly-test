package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.form.UserBaseInfoForm;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;

/**
 * UserBaseInfoDao 
 */
public interface UserBaseInfoDao {
    
    /**
     * 查询 UserBaseInfo
     */
    List<UserBaseInfo> find(final UserBaseInfoQuery query);

    /**
     * 通过id得到一个 UserBaseInfo
     */
    UserBaseInfo get(final String id);
    
    /**
     * 新增 UserBaseInfo
     */
    void add(final UserBaseInfoForm form);

    /**
     * 修改 UserBaseInfo
     */
    int update(final UserBaseInfoForm form);
    
    /**
     * 删除一个 UserBaseInfo
     */
    int delete(final String id);
    
    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}
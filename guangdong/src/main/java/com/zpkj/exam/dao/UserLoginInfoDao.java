package com.zpkj.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;

/**
 * UserLoginInfoDao
 */
public interface UserLoginInfoDao {

    /**
     * 查询 UserLoginInfo
     */
    List<UserLoginInfo> find(final UserLoginInfoQuery query);

    /**
     * 通过id得到一个 UserLoginInfo
     */
    UserLoginInfo get(final String id);

    /**
     * 新增 UserLoginInfo
     */
    void add(final UserLoginInfoForm form);

    /**
     * 修改 UserLoginInfo
     */
    int update(final UserLoginInfoForm form);

    /**
     * 删除一个 UserLoginInfo
     */
    int delete(final String id);

    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 通过userId修改密码
     */
    int updatePwdByUserId(@Param("userId") final String userId, @Param("pwd") final String pwd);

    /**
     * 通过userId修改头像
     */
    int updateHeadImgByUserId(@Param("headImg") final String headImg, @Param("userId") final String userId);

    /**
     * 通过userId修改account
     */
    int updateAccountByUserId(@Param("account") final String account, @Param("userId") final String userId);

    void updateLoginByUserId(UserLoginInfoForm form);
}
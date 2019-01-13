package com.zpkj.exam.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;

/**
 * UserLoginInfoService
 */
public interface UserLoginInfoService {

    /**
     * 查询 UserLoginInfo
     */
    List<UserLoginInfo> find(final UserLoginInfoQuery query);

    /**
     * 分页查询 UserLoginInfo
     */
    Page<UserLoginInfo> findByPage(final UserLoginInfoQuery query);

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
    int changeStatus(final String id, final Integer status);

    /**
     * 通过userId修改密码
     */
    int updatePwdByUserId(final String userId, final String pwd);

    /**
     * 通过userId修改头像
     */
    int updateHeadImgByUserId(final String headImg, final String userId);

    /**
     * 通过userId修改Account
     */
    int updateAccountByUserId(final String account, final String userId);

    void updateLoginByUserId(UserLoginInfoForm form);
}
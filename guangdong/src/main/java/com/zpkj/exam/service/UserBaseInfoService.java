package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.form.UserBaseInfoForm;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;

/**
 * UserBaseInfoService
 */
public interface UserBaseInfoService {

    /**
     * 查询 UserBaseInfo
     */
    List<UserBaseInfo> find(final UserBaseInfoQuery query);

    /**
     * 分页查询 UserBaseInfo
     */
    Page<UserBaseInfo> findByPage(final UserBaseInfoQuery query);

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
    int changeStatus(final String id, final Integer status);

}
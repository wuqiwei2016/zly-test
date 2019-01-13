package com.zpkj.exam.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zpkj.exam.domain.PermissionInfo;
import com.zpkj.exam.domain.form.PermissionInfoForm;
import com.zpkj.exam.domain.query.PermissionInfoQuery;

/**
 * PermissionInfoService
 */
public interface PermissionInfoService {

    /**
     * 查询 PermissionInfo
     */
    List<PermissionInfo> find(final PermissionInfoQuery query);

    /**
     * 分页查询 PermissionInfo
     */
    Page<PermissionInfo> findByPage(final PermissionInfoQuery query);

    /**
     * 通过id得到一个 PermissionInfo
     */
    PermissionInfo get(final String id);

    /**
     * 新增 PermissionInfo
     */
    void add(final PermissionInfoForm form);

    /**
     * 修改 PermissionInfo
     */
    int update(final PermissionInfoForm form);

    /**
     * 删除一个 PermissionInfo
     */
    int delete(final String id);

    /**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

    /**
     * 通过 userId 修改 PermissionInfo 的权限
     */
    int updatePermissionByUserId(final String userId, final String permission);

}
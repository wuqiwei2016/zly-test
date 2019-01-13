package com.zpkj.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.PermissionInfo;
import com.zpkj.exam.domain.form.PermissionInfoForm;
import com.zpkj.exam.domain.query.PermissionInfoQuery;

/**
 * PermissionInfoDao
 */
public interface PermissionInfoDao {

    /**
     * 查询 PermissionInfo
     */
    List<PermissionInfo> find(final PermissionInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 通过 userId 修改 PermissionInfo 的权限
     */
    int updatePermissionByUserId(@Param("userId") final String userId, @Param("permission") final String permission);
}
package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.PermissionInfo;
import com.zpkj.exam.domain.form.PermissionInfoForm;
import com.zpkj.exam.domain.query.PermissionInfoQuery;
import com.zpkj.exam.dao.PermissionInfoDao;
import com.zpkj.exam.service.PermissionInfoService;


/**
 * PermissionInfoService
 */
@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {
    @Autowired
    private PermissionInfoDao permissionInfoDao;

    /**
     * 查询 PermissionInfo
     */
    public List<PermissionInfo> find(final PermissionInfoQuery query) {
        return permissionInfoDao.find(query);
    }

    /**
     * 分页查询 PermissionInfo
     */
    public Page<PermissionInfo> findByPage(final PermissionInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<PermissionInfo>) permissionInfoDao.find(query);
    }

    /**
     * 通过id得到一个 PermissionInfo
     */
    public PermissionInfo get(final String id) {
        return permissionInfoDao.get(id);
    }

    /**
     * 新增 PermissionInfo
     */
    public void add(final PermissionInfoForm form) {
        permissionInfoDao.add(form);
    }

    /**
     * 修改 PermissionInfo
     */
    public int update(final PermissionInfoForm form) {
        return permissionInfoDao.update(form);
    }

    /**
     * 删除一个 PermissionInfo
     */
    public int delete(final String id) {
        return permissionInfoDao.delete(id);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return permissionInfoDao.changeStatus(id, status);
    }

    /**
     * 通过 userId 修改 PermissionInfo 的权限
     */
    public int updatePermissionByUserId(final String userId, final String permission) {
        return permissionInfoDao.updatePermissionByUserId(userId, permission);
    }

}
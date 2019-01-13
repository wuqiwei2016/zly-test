package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;
import com.zpkj.exam.dao.UserLoginInfoDao;
import com.zpkj.exam.service.UserLoginInfoService;


/**
 * UserLoginInfoService
 */
@Service
public class UserLoginInfoServiceImpl implements UserLoginInfoService {
    @Autowired
    private UserLoginInfoDao userLoginInfoDao;

    /**
     * 查询 UserLoginInfo
     */
    public List<UserLoginInfo> find(final UserLoginInfoQuery query) {
        return userLoginInfoDao.find(query);
    }

    /**
     * 分页查询 UserLoginInfo
     */
    public Page<UserLoginInfo> findByPage(final UserLoginInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<UserLoginInfo>) userLoginInfoDao.find(query);
    }

    /**
     * 通过id得到一个 UserLoginInfo
     */
    public UserLoginInfo get(final String id) {
        return userLoginInfoDao.get(id);
    }

    /**
     * 新增 UserLoginInfo
     */
    public void add(final UserLoginInfoForm form) {
        userLoginInfoDao.add(form);
    }

    /**
     * 修改 UserLoginInfo
     */
    public int update(final UserLoginInfoForm form) {
        return userLoginInfoDao.update(form);
    }

    /**
     * 删除一个 UserLoginInfo
     */
    public int delete(final String id) {
        return userLoginInfoDao.delete(id);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return userLoginInfoDao.changeStatus(id, status);
    }

    /**
     * 通过userId修改密码
     */
    public int updatePwdByUserId(final String userId, final String pwd) {
        return userLoginInfoDao.updatePwdByUserId(userId, pwd);
    }

    /**
     * 通过userId修改头像
     */
    public int updateHeadImgByUserId(final String headImg, final String pwd) {
        return userLoginInfoDao.updateHeadImgByUserId(headImg, pwd);
    }

    /**
     * 通过userId修改Account
     */
    public int updateAccountByUserId(final String account, final String pwd) {
        return userLoginInfoDao.updateAccountByUserId(account, pwd);
    }

    @Override
    public void updateLoginByUserId(UserLoginInfoForm form) {
        userLoginInfoDao.updateLoginByUserId(form);
    }
}
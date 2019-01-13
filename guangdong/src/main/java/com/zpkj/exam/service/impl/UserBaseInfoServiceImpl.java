package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.form.UserBaseInfoForm;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;
import com.zpkj.exam.dao.UserBaseInfoDao;
import com.zpkj.exam.service.UserBaseInfoService;


/**
 * UserBaseInfoService 
 */
@Service
public class UserBaseInfoServiceImpl implements UserBaseInfoService {
    @Autowired 
    private UserBaseInfoDao userBaseInfoDao;
	
	/**
     * 查询 UserBaseInfo
     */
    public List<UserBaseInfo> find(final UserBaseInfoQuery query) {
        return userBaseInfoDao.find(query);
    }
    
    /**
     * 分页查询 UserBaseInfo
     */
    public Page<UserBaseInfo> findByPage(final UserBaseInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<UserBaseInfo>) userBaseInfoDao.find(query);
    }

    /**
     * 通过id得到一个 UserBaseInfo
     */
    public UserBaseInfo get(final String id) {
        return userBaseInfoDao.get(id);
    }
    
    /**
     * 新增 UserBaseInfo
     */
    public void add(final UserBaseInfoForm form) {
        userBaseInfoDao.add(form);
    }

    /**
     * 修改 UserBaseInfo
     */
    public int update(final UserBaseInfoForm form) {
        return userBaseInfoDao.update(form);
    }
    
    /**
     * 删除一个 UserBaseInfo
     */
    public int delete(final String id) {
        return userBaseInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return userBaseInfoDao.changeStatus(id, status);
    }
	
}
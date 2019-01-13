package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.ArticleInfo;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.query.ArticleInfoQuery;
import com.zpkj.exam.dao.ArticleInfoDao;
import com.zpkj.exam.service.ArticleInfoService;


/**
 * ArticleInfoService
 */
@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    @Autowired
    private ArticleInfoDao articleInfoDao;

    /**
     * 查询 ArticleInfo
     */
    public List<ArticleInfo> find(final ArticleInfoQuery query) {
        return articleInfoDao.find(query);
    }

    /**
     * 分页查询 ArticleInfo
     */
    public Page<ArticleInfo> findByPage(final ArticleInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<ArticleInfo>) articleInfoDao.find(query);
    }

    /**
     * 通过id得到一个 ArticleInfo
     */
    public ArticleInfo get(final String id) {
        return articleInfoDao.get(id);
    }

    /**
     * 新增 ArticleInfo
     */
    public void add(final ArticleInfoForm form) {
        articleInfoDao.add(form);
    }

    /**
     * 修改 ArticleInfo
     */
    public int update(final ArticleInfoForm form) {
        return articleInfoDao.update(form);
    }

    /**
     * 删除一个 ArticleInfo
     */
    public int delete(final String id) {
        return articleInfoDao.delete(id);
    }

    /**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return articleInfoDao.changeStatus(id, status);
    }

    /**
     * 通过ownerId得到一个 ArticleInfo
     */
    public ArticleInfo getByOwnerId(final String ownerId) {
        return articleInfoDao.getByOwnerId(ownerId);
    }
}
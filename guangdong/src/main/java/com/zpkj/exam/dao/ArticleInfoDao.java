package com.zpkj.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.ArticleInfo;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.query.ArticleInfoQuery;

/**
 * ArticleInfoDao
 */
public interface ArticleInfoDao {

    /**
     * 查询 ArticleInfo
     */
    List<ArticleInfo> find(final ArticleInfoQuery query);

    /**
     * 通过id得到一个 ArticleInfo
     */
    ArticleInfo get(final String id);

    /**
     * 新增 ArticleInfo
     */
    void add(final ArticleInfoForm form);

    /**
     * 修改 ArticleInfo
     */
    int update(final ArticleInfoForm form);

    /**
     * 删除一个 ArticleInfo
     */
    int delete(final String id);

    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    /**
     * 通过ownerId得到一个 ArticleInfo
     */
    ArticleInfo getByOwnerId(final String ownerId);
}
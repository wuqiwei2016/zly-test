package com.zpkj.exam.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * ArticleInfoForm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String title; // 标题
    private String author; // 作者
    private String source; // 来源
    private String content; // 内容
    private Integer sortOrder; // 排序
    private Integer contentType; // 组合方式 ：1.文字、2.图片+文字3.图片
    private String articleType; // 分类：1通知 2公告 3新闻
    private Date releaseTime = new Date(); // 发布时间
    private String attachment; // 附件
    private String coverImg; // 封面图片
    private String extLink; // 外部链接
    private Integer isRecommended; // 是否推荐
    private Integer isTop; // 是否置顶
    private String ownerId; // 拥有者id
    private Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private Date updateTime = new Date(); // 更新时间
    private String updateUsername; // 更新人
    private Integer status = 1; // 状态
}
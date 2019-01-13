package com.zpkj.exam.domain.query;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ArticleInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 文章表
    private String title; // 标题
    private String author; // 作者
    private String source; // 来源
    private String content; // 内容
    private Integer sortOrder; // 排序
    private Integer contentType; // 组合方式 ：1.文字、2.图片+文字3.图片
    private String articleType; // 分类：1通知 2公告 3新闻
    private java.util.Date releaseTime; // 发布时间
    private String attachment; // 附件
    private String coverImg; // 封面图片
    private String extLink; // 外部链接
    private Integer isRecommended; // 是否推荐
    private Integer isTop; // 是否置顶
    private String ownerId; // 拥有者id
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
	
    private Integer page = 1;
    private Integer limit = 20;
    private String search;
    private Integer noticeType;

    public Integer GetnoticeType(){
        if (articleType.equals("2") && title.contains("考试通过的公告")){
            noticeType=1;
        }
        return noticeType;
    }
}
package com.zpkj.exam.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * ArticleInfo 
 */
@Data
public class ArticleInfo implements Serializable {
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

    private ExamPeriodInfo examPeriodInfo; // 考试期次信息
    private List<ExamInfo> examInfoList; // 考试信息
    private String attachName; // 附加名称
    private ExamInfo examInfo; // 考试信息
    private Integer noticeType;

    public Integer getNoticeType() {
        //查询从发布之日是否超过三天  如果不是则为最新 返回 1
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(createTime);
        cal.add(Calendar.MINUTE, 72*60);// 24小时制
        createTime = cal.getTime();
        cal = null;
        if (createTime.compareTo(date)<1){
            return 1;
        }
        return 0;
    }
}
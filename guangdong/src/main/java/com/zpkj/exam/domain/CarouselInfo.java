package com.zpkj.exam.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * CarouselInfo 
 */
@Data
public class CarouselInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 轮播图表
    private String imgPath; // 图片
    private String title; // 标题
    private String link; // 链接
    private Integer sortOrder; // 排序
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
}
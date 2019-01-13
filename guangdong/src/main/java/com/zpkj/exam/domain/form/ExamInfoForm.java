package com.zpkj.exam.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * ExamInfoForm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String userId; // userId
    private String examPeriodId; // 考试期次id
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private String totalScore; // 总分
    private Integer result; // 结果 1 通过 2 未通过
    private String picture1; // 抓拍图片1
    private String picture2; // 抓拍图片2
    private String picture3; // 抓拍图片3
    private Integer isInform = 2; // 是否通知 1 通知 2 未通知
    private Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private Date updateTime = new Date(); // 更新时间
    private String updateUsername; // 更新人
    private Integer status = 1; // 状态

    private String realName; // 姓名
    private String idCard; // 身份证号
}
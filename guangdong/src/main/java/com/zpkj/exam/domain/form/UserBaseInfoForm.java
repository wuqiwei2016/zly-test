package com.zpkj.exam.domain.form;

import java.io.Serializable;

import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;
import java.util.Date;

/**
 * UserBaseInfoForm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBaseInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    @NotBlank(message = "姓名不能为空")
    @Length(max = 45, message = "姓名最大长度在45字以内")
    private String realName; // 真实姓名
    @NotBlank(message = "身份证号不能为空")
    @Length(max = 45, message = "身份证号最大长度在45字以内")
    private String idCard; // 身份证号
    private Integer sex; // 性别 1男 2女
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday; // 生日
    @NotBlank(message = "手机号不能为空")
    @Length(max = 45, message = "手机号最大长度在45字以内")
    private String phone; // 电话
    @Length(max = 45, message = "邮箱最大长度在45字以内")
    private String email; // 邮箱
    private String province; // 省
    private String city; // 市
    private String area; // 区
    @Length(max = 255, message = "地址最大长度在255字以内")
    private String address; // 地址
    private String education; // 学历
    @Length(max = 45, message = "工作单位最大长度在45字以内")
    private String workUnit; // 工作单位
    @Length(max = 45, message = "邮政编码最大长度在45字以内")
    private String postCode; // 邮政编码
    @Length(max = 45, message = "固定电话最大长度在45字以内")
    private String fixedTelephone; // 固定电话
    private String position; // 职位
    private String workNumber; // 工号
    private Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private Date updateTime = new Date(); // 更新时间
    private String updateUsername; // 更新人
    private Integer status = 1; // 状态
    private String peopleType1; // 人员类型1
    private String peopleType2; // 人员类型2
    private String peopleType3; // 人员类型3
//    private Timestamp birthdays;
    private String headImg; // 头像
    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址
}
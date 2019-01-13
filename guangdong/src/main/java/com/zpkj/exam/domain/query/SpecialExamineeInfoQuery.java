package com.zpkj.exam.domain.query;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SpecialExamineeInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SpecialExamineeInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 特殊考生表
    private String realName; // 真实姓名
    private String idCard; // 身份证号
    private String phone; // 电话
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
	
    private Integer page = 1;
    private Integer limit = 20;
    private String search;
}
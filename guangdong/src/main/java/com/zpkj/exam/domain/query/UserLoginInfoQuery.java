package com.zpkj.exam.domain.query;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserLoginInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 用户登录信息表
    private String userId; // 用户id
    private String account; // 账号
    private Integer accountType; // 账号类型 1手机号
    private String pwd; // 密码
    private String nickName; // 昵称
    private String userDesc; // 简介、签名等
    private String headImg; // 头像
    private String provinceId; // 省份ID
    private String cityId; // 城市ID
    private String deviceId; // 唯一设备ID
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
	
    private Integer page = 1;
    private Integer limit = 20;
    private String search;
}
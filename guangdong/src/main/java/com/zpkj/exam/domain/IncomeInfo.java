package com.zpkj.exam.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * IncomeInfo 
 */
@Data
public class IncomeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title; // 标题
    private String userBaseId; // 用户基本信息id
    private String outTradeNo; // 订单号
    private BigDecimal moneyAmount; // 支付金额
    private String tradeNo;//支付宝交易号
    private Date payTime; // 支付时间
    private String status; // 状态（1已付款、2未付款）
    private Date createTime; // 创建时间
    private Date updateTime; // 修改时间
    private String createUsername; // 创建人
    private String updateUsername; // 修改人
    private String remark;//备注

    private String realName;
    private String idCard;
    private String phone;
    private String peopleType1;
    private String peopleType2;
}
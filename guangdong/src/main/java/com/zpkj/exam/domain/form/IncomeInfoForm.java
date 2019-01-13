package com.zpkj.exam.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * IncomeInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IncomeInfoForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String title; // 标题
	private String userBaseId; // 用户基本信息id
	private String outTradeNo; // 订单号
	private BigDecimal moneyAmount ; // 支付金额
	private String tradeNo;//支付宝交易号
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime; // 支付时间
	private String status = "2"; // 状态（1已付款、2未付款）
	private Date createTime = new Date(); // 创建时间
	private Date updateTime; // 修改时间
	private String createUsername; // 创建人
	private String updateUsername; // 修改人
	private String remark;//备注
}
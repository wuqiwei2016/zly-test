package com.zpkj.exam.controller.fore;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.IncomeInfo;
import com.zpkj.exam.domain.form.IncomeInfoForm;
import com.zpkj.exam.domain.query.IncomeInfoQuery;
import com.zpkj.exam.service.IncomeInfoService;
import com.zpkj.exam.util.alipay.config.AlipayConfig;
import com.zpkj.exam.util.alipay.util.AlipayNotify;
import com.zpkj.exam.util.alipay.util.AlipaySubmit;
import com.zpkj.exam.util.alipay.util.UtilDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/** 支付宝支付
 * Created by wuqiw on 2018/8/20.
 */
@Controller
@RequestMapping("/alipay")
@Slf4j
@CrossOrigin
public class AlipayController {

    @Autowired
    private IncomeInfoService incomeInfoService;
    @Value("${systemPara.moneyAmount}")
    private BigDecimal moneyAmount;
    /**
     * 组装支付宝支付的数据(添加订单及发票信息)
     */
    @RequestMapping(value = "/toPay", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean Pay(final IncomeInfoForm incomeInfoForm, final HttpServletResponse response) throws IOException {
        //处理业务
        String incomeId = "";
        IncomeInfoQuery query = new IncomeInfoQuery();
        query.setUserBaseId(incomeInfoForm.getUserBaseId());
        List<IncomeInfo> list = incomeInfoService.find(query);
        if(list != null && list.size()>0){
            incomeId = list.get(0).getId();
        }else{
            incomeInfoForm.setMoneyAmount(moneyAmount);
            incomeInfoService.add(incomeInfoForm);
            incomeId = incomeInfoForm.getId();
        }
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = UtilDate.getOrderNum();
        // 订单名称，必填
        String subject = AlipayConfig.tradeTitle;
        // 付款金额，必填
         /*   String totalFee = incomeInfoForm.getMoneyAmount()+ "";*/
        String totalFee = moneyAmount + "";//测试购买1分钱
        //订单描述
        String body = "";
        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.sellerId);
        sParaTemp.put("_input_charset", AlipayConfig.inputCharset);
        sParaTemp.put("payment_type", AlipayConfig.paymentType);
        sParaTemp.put("notify_url", AlipayConfig.notifyUrl);
        sParaTemp.put("return_url", AlipayConfig.returnUrl);
        sParaTemp.put("anti_phishing_key", AlipayConfig.antiPhishingKey);
        sParaTemp.put("exter_invoke_ip", AlipayConfig.exterInvokeIp);
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", totalFee);
        sParaTemp.put("body", body);
        sParaTemp.put("extra_common_param", incomeId); // 返回的用户参数
        // 其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        // 如sParaTemp.put("参数名","参数值");
        // 建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "OK");
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write(sHtmlText);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean();
    }

    /**
     * 成功页面
     */
    @RequestMapping(value = "/paySuccess.htm", method = RequestMethod.GET)
    public final String paySuccess() {
        return "paySuccess";
    }

    /**
     * 支付完成(同步)回调函数
     */
    @RequestMapping(value = "/callback0.htm", method = RequestMethod.GET)
    public ModelAndView returnmethod(final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("进入到支付宝回调函数callback0");
        ResultBean resultBean = new ResultBean();
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:http://gd-exam.zp-kj.com/pages/test-index.html"); // 返回页面
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        // 支付宝交易号
        String tradeNo = params.get("trade_no");
        // 交易状态
        String tradeStatus = params.get("trade_status");
        // subject商品名称
        String subject = params.get("subject");
        // 商品描述
        String body = params.get("body");
        // income id
        String inComeId = params.get("extra_common_param");
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        log.info("同步支付宝支付参数out_trade_no:{},trade_no:{},trade_status:{},body:{},inComeId:{}", tradeNo, tradeNo, tradeStatus, body, inComeId);
        if (AlipayNotify.verify(params)) { //验证成功
            // 请在这里加上商户的业务逻辑程序代码
            //if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {//当支付时间成功后或者三个月后不能退款后更新支付时间
            if ( tradeStatus.equals("TRADE_SUCCESS")) {//只有当付款成功后才update支付之间
                log.info("进入到成功支付页面");
                IncomeInfoForm incomeForm = new IncomeInfoForm();
                incomeForm.setId(inComeId);
                incomeForm.setOutTradeNo(outTradeNo);
                incomeForm.setTradeNo(tradeNo);
                incomeForm.setStatus("1");
                incomeForm.setUpdateTime(new Date());
                incomeForm.setPayTime(new Date());
                int n = incomeInfoService.update(incomeForm);
                return view;
                /* if (1 == n) {
                    return resultBean;
                } else {
                    resultBean.setCode("1");
                }*/
            }
        } else { // 验证失败
            resultBean.setCode(1);
            log.info("支付宝支付失败,支付incomeid{},时间：{}", inComeId, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        return view;
    }

    /**
     * 支付完成(异步)回调函数（处理业务逻辑）
     *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/callback.htm", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView notifymethod(final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("进入到支付宝回调函数callback0");
        ResultBean resultBean = new ResultBean();
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:http://gd-exam.zp-kj.com/pages/test-index.html"); // 返回页面
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        // 支付宝交易号
        String tradeNo = params.get("trade_no");
        // 交易状态
        String tradeStatus = params.get("trade_status");
        // subject商品名称
        String subject = params.get("subject");
        // 商品描述
        String body = params.get("body");
        // income id
        String inComeId = params.get("extra_common_param");
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        log.info("同步支付宝支付参数out_trade_no:{},trade_no:{},trade_status:{},body:{},inComeId:{}", tradeNo, tradeNo, tradeStatus, body, inComeId);
        if (AlipayNotify.verify(params)) { //验证成功
            // 请在这里加上商户的业务逻辑程序代码
            //if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {//当支付时间成功后或者三个月后不能退款后更新支付时间
            if ( tradeStatus.equals("TRADE_SUCCESS")) {//只有当付款成功后才update支付之间
                log.info("进入到成功支付页面");
                IncomeInfoForm incomeForm = new IncomeInfoForm();
                incomeForm.setId(inComeId);
                incomeForm.setOutTradeNo(outTradeNo);
                incomeForm.setTradeNo(tradeNo);
                incomeForm.setStatus("1");
                incomeForm.setUpdateTime(new Date());
                incomeForm.setPayTime(new Date());
                int n = incomeInfoService.update(incomeForm);
                return view;
                /* if (1 == n) {
                    return resultBean;
                } else {
                    resultBean.setCode("1");
                }*/
            }
        } else { // 验证失败
            resultBean.setCode(1);
            log.info("支付宝支付失败,支付incomeid{},时间：{}", inComeId, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        return view;
    }

}

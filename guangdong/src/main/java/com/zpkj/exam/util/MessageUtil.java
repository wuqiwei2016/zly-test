package com.zpkj.exam.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by Administrator on 2018/5/29.
 */
public class MessageUtil {
    /**
     * 发送短信
     */
    public static void sendMessage(final String signName, final String templateCode, final String msg, final String cellphone) {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "24527885", "c2e1c91f36e114dc769a7f5a1d5febaa");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
        req.setSmsParamString(msg);
        req.setRecNum(cellphone);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
            /*
             * JSONObject object = new JSONObject(rsp.getBody()); JSONObject object1 = (JSONObject) object.get("alibaba_aliqin_fc_sms_num_send_response"); JSONObject result = (JSONObject) object1.get("result");
             */
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}

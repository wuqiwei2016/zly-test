package com.zpkj.exam.controller;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.IncomeInfo;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.IncomeInfoQuery;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.service.IncomeInfoService;
import com.zpkj.exam.service.UserBaseInfoService;
import com.zpkj.exam.service.UserLoginInfoService;
import com.zpkj.exam.util.GsonUtils;
import com.zpkj.exam.util.JWTUtil;
import com.zpkj.exam.util.MessageUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("")
@CrossOrigin
public class IndexController {

    @Autowired
    UserLoginInfoService userLoginInfoService;
    @Autowired
    UserBaseInfoService userBaseInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IncomeInfoService incomeInfoService;

    @ApiOperation(value = "获取验证码", notes = "用户通过输入手机号获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public ResultBean getCode(String phone) {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        MessageUtil.sendMessage("登录验证", "SMS_69120159", "{\"code\":\"" + code + "\",\"product\":\"【广东省三类人员模拟考试系统】\"}", phone);
        redisTemplate.opsForValue().set(phone, "" + code, 60 * 60, TimeUnit.SECONDS);
        return new ResultBean();
    }

    @ApiOperation(value = "通过手机号、验证码登录", notes = "用户通过手机号、验证码方式登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/loginByCode", method = RequestMethod.POST)
    public ResultBean loginByCode(String phone, String code) {
        String oldCode = redisTemplate.opsForValue().get(phone);
        if (code.equals(oldCode)) {
            Map map = new HashMap<String, Object>();
            //查询用户信息
            UserLoginInfoQuery query = new UserLoginInfoQuery();
            query.setAccount(phone);
            query.setAccountType(1);
            List<UserLoginInfo> list = userLoginInfoService.find(query);
            if (list.size() > 0) {
                //有用户信息
                UserLoginInfo userLoginInfo = list.get(0);
                map.put("loginId", userLoginInfo.getId());
                map.put("userId", userLoginInfo.getUserId());
                String accessToken = JWTUtil.createJWT(UUID.randomUUID().toString(), map, 24 * 60 * 60 * 1000);
                //判断是否已经交费
                boolean flag = false;
                IncomeInfoQuery query1 = new IncomeInfoQuery();
                query1.setUserBaseId(userLoginInfo.getUserId());
                List<IncomeInfo> incomeInfos = incomeInfoService.find(query1);
                if (incomeInfos.size() > 0) {
                    IncomeInfo incomeInfo = incomeInfos.get(0);
                    if ("1".equals(incomeInfo.getStatus())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    //未交费
                    UserBaseInfo user = userBaseInfoService.get(userLoginInfo.getUserId());
                    return new ResultBean(3, accessToken, user);
                } else {
                    //已交费
                    return new ResultBean(0, accessToken);
                }
            } else {
                //没有用户信息
                String accessToken = JWTUtil.createJWT(UUID.randomUUID().toString(), map, 24 * 60 * 60 * 1000);
                return new ResultBean(2, accessToken);
            }
        } else {
            return new ResultBean(1, "验证码错误");
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户通过用户名密码进行登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(String username, String password) {
        UserLoginInfoQuery query = new UserLoginInfoQuery();
        query.setAccount(username);
        query.setPwd(password);
        query.setAccountType(4);
        List<UserLoginInfo> list = userLoginInfoService.find(query);
        if (list.size() != 0) {
            Map map = new HashMap<String, Object>();
            UserLoginInfo userLoginInfo = list.get(0);
            UserBaseInfo userBaseInfo = userBaseInfoService.get(list.get(0).getUserId());
            if (userBaseInfo != null && userBaseInfo.getStatus() == 2) {
                return new ResultBean(1, "账号已被禁用");
            }
            map.put("enabled", true);
            map.put("loginId", userLoginInfo.getId());
            map.put("userId", userLoginInfo.getUserId());
            String accessToken = JWTUtil.createJWT(UUID.randomUUID().toString(), map, 24 * 60 * 60 * 1000);
            return new ResultBean(0, accessToken);
        } else {
            return new ResultBean(1, "登录失败");
        }
    }

    @ApiOperation(value = "检测手机号是否注册", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
    public boolean checkPhone(String phone) {
        UserLoginInfoQuery query = new UserLoginInfoQuery();
        query.setAccount(phone);
        query.setAccountType(1);
        List<UserLoginInfo> list = userLoginInfoService.find(query);
        return list.size() > 0;
    }

    @ApiOperation(value = "检测用户名是否注册", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
    public boolean checkUserName(String userName) {
        UserLoginInfoQuery query = new UserLoginInfoQuery();
        query.setAccount(userName);
        query.setAccountType(4);
        List<UserLoginInfo> list = userLoginInfoService.find(query);
        return list.size() > 0;
    }

    @ApiOperation(value = "检测身份证号是否注册", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", value = "身份证号", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/checkIdCard", method = RequestMethod.POST)
    public boolean checkIdCard(String idCard) {
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setIdCard(idCard);
        List<UserBaseInfo> list = userBaseInfoService.find(query);
        return list.size() > 0;
    }
}

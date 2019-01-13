package com.zpkj.exam.interceptor;

import com.zpkj.exam.util.GsonUtils;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2018/6/6.
 */
@Component
public class AccessTokenVerifyInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean flag = false;
        if (request.getMethod().equals("OPTIONS")) {
            flag = true;
        } else {
            // token
            String accessToken = request.getParameter("accessToken");
            // 验证
            try {
                String a = request.getMethod();
                Claims claims = JWTUtil.parseJWT(accessToken);
                if (claims.get("userId") != null || (request.getRequestURI().equals("/f/user") && request.getMethod().equals("POST"))) {
                    flag = true;
                }
            } catch (Exception e) {
            }
        /*if (accessToken != null && redisTemplate.hasKey(accessToken)) {
            UserBaseInfo user = (UserBaseInfo) GsonUtils.getInstance().fromJson(redisTemplate.opsForValue().get(accessToken), UserBaseInfo.class);
            // 时间过期
            // 用户验证
            if (user != null) {
                request.setAttribute("user", user);
                redisTemplate.opsForValue().set(accessToken, GsonUtils.getInstance().toJson(user), 60 * 10, TimeUnit.SECONDS);
                flag = true;
            }
        }*/
        }
        if (!flag) {
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            //response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print(GsonUtils.getInstance().toJson(new ResultBean(1001, "认证失败")));
        }

        return flag;
    }
}

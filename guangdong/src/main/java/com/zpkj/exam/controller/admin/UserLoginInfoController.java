package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;
import com.zpkj.exam.service.UserLoginInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/userLoginInfo")
@CrossOrigin
public class UserLoginInfoController {

    @Autowired
    UserLoginInfoService userLoginInfoService;

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getUserLoginInfoList(@ModelAttribute UserLoginInfoQuery query) {
        return new ResultBean(userLoginInfoService.find(query));
    }

    @ApiOperation(value = "创建用户", notes = "根据UserLoginInfo对象创建用户")
    @ApiImplicitParam(name = "userLoginInfoForm", value = "用户详细实体UserLoginInfoForm", required = true, dataType = "UserLoginInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postUserLoginInfo(UserLoginInfoForm userLoginInfoForm) {
        userLoginInfoService.add(userLoginInfoForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getUserLoginInfoById(@PathVariable("id") String id) {
        return new ResultBean(userLoginInfoService.get(id));
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的userLoginInfo信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userLoginInfoForm", value = "用户详细实体UserLoginInfoform", required = true, dataType = "UserLoginInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateUserLoginInfo(@PathVariable("id") String id, UserLoginInfoForm userLoginInfoform) {
        userLoginInfoService.update(userLoginInfoform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteUserLoginInfo(@PathVariable("id") String id) {
        userLoginInfoService.delete(id);
        return new ResultBean();
    }
}

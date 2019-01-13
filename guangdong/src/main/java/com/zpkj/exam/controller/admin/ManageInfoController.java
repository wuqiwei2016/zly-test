package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.PermissionInfoForm;
import com.zpkj.exam.domain.form.UserBaseInfoForm;
import com.zpkj.exam.domain.form.UserLoginInfoForm;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;
import com.zpkj.exam.service.PermissionInfoService;
import com.zpkj.exam.service.UserBaseInfoService;
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
@RequestMapping("/m/manage")
@CrossOrigin
public class ManageInfoController {

    @Autowired
    UserBaseInfoService userBaseInfoService;
    @Autowired
    UserLoginInfoService userLoginInfoService;
    @Autowired
    PermissionInfoService permissionInfoService;

    @ApiOperation(value = "获取管理员列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getUserBaseInfoList(@ModelAttribute UserBaseInfoQuery query) {
        query.setUserType("2");
        return new ResultBean(userBaseInfoService.findByPage(query));
    }

    @ApiOperation(value = "创建管理员", notes = "根据UserBaseInfo对象创建管理员")
    @ApiImplicitParam(name = "manageForm", value = "管理员详细实体UserBaseInfoForm", required = true, dataType = "UserBaseInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postUserBaseInfo(UserBaseInfoForm manageForm, String account, String pwd, String permission) {
        //创建管理员信息
        userBaseInfoService.add(manageForm);
        //创建用户登录信息
        UserLoginInfoForm form = new UserLoginInfoForm();
        form.setAccount(account);
        form.setPwd(pwd);
        form.setAccountType(4);
        form.setUserId(manageForm.getId());
        userLoginInfoService.add(form);
        //创建权限信息
        PermissionInfoForm permissionInfoForm = new PermissionInfoForm();
        permissionInfoForm.setUserId(manageForm.getId());
        permissionInfoForm.setUserType("2");
        permissionInfoForm.setPermission(permission);
        permissionInfoService.add(permissionInfoForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取管理员详细信息", notes = "根据url的id来获取管理员详细信息")
    @ApiImplicitParam(name = "id", value = "管理员ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getUserBaseInfoById(@PathVariable("id") String id) {
        return new ResultBean(userBaseInfoService.get(id));
    }

    @ApiOperation(value = "更新管理员详细信息", notes = "根据url的id来指定更新对象，并根据传过来的manage信息来更新管理员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "manageForm", value = "管理员详细实体UserBaseInfoform", required = true, dataType = "UserBaseInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateUserBaseInfo(@PathVariable("id") String id, UserBaseInfoForm manageform, String pwd, String permission) {
        userBaseInfoService.update(manageform);
        userBaseInfoService.changeStatus(id, manageform.getStatus());
        permissionInfoService.updatePermissionByUserId(id, permission);
        userLoginInfoService.updatePwdByUserId(id, pwd);
        return new ResultBean();
    }

    @ApiOperation(value = "删除管理员", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "管理员ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteUserBaseInfo(@PathVariable("id") String id) {
        //删除管理员
        userBaseInfoService.delete(id);
        //删除管理员登录信息
        return new ResultBean();
    }
}

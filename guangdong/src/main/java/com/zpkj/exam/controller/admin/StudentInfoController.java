package com.zpkj.exam.controller.admin;

import com.github.pagehelper.Page;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.AttachmentInfo;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.domain.form.*;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;
import com.zpkj.exam.domain.query.IncomeInfoQuery;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;
import com.zpkj.exam.domain.query.UserLoginInfoQuery;
import com.zpkj.exam.service.*;
import com.zpkj.exam.util.ExcelUtils;
import com.zpkj.exam.util.ValidateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/studentInfo")
@CrossOrigin
public class StudentInfoController {

    @Autowired
    UserBaseInfoService userBaseInfoService;
    @Autowired
    UserLoginInfoService userLoginInfoService;
    @Autowired
    PermissionInfoService permissionInfoService;
    @Autowired
    ExamApplyInfoService examApplyInfoService;
    @Autowired
    UploadService uploadService;
    @Autowired
    AttachmentInfoService attachmentInfoService;
    @Autowired
    IncomeInfoService incomeInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentInfoController.class);

    @ApiOperation(value = "获取考生列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getUserBaseInfoList(@ModelAttribute UserBaseInfoQuery query) {
        query.setUserType("3");
        return new ResultBean(userBaseInfoService.findByPage(query));
    }

    @ApiOperation(value = "获取交费考生列表", notes = "")
    @RequestMapping(value = "/income", method = RequestMethod.GET)
    public ResultBean getIncomeList(@ModelAttribute IncomeInfoQuery query) {
        query.setStatus("1");
        return new ResultBean(incomeInfoService.findByPage(query));
    }

    @ApiOperation(value = "获取考生列表", notes = "")
    @RequestMapping(value = "/flag", method = RequestMethod.GET)
    public ResultBean getList(ExamApplyInfoQuery query) {
        LOGGER.info("打印获取考生列表的分页信息：" + query.toString());
        if (query.getFlag() != null && query.getFlag() != 2 && query.getFlag() != 3) {
            query.setFlag(1);
        } else if (query.getFlag() == null) {
            query.setFlag(1);
        }
        query.setCreateTime(new Date());
        return new ResultBean(examApplyInfoService.find(query));
    }

    @ApiOperation(value = "创建考生", notes = "根据UserBaseInfo对象创建考生")
    @ApiImplicitParam(name = "studentInfoForm", value = "考生详细实体UserBaseInfoForm", required = true, dataType = "UserBaseInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postUserBaseInfo(UserBaseInfoForm studentInfoForm) {
        //创建考生信息
        userBaseInfoService.add(studentInfoForm);
        //创建用户登录信息
        UserLoginInfoForm form = new UserLoginInfoForm();
        form.setAccount(studentInfoForm.getPhone());
        form.setAccountType(1);
        form.setUserId(studentInfoForm.getId());
        form.setHeadImg(studentInfoForm.getHeadImg());
        form.setPeopleType(studentInfoForm.getPeopleType());
        form.setAnnexUrl(studentInfoForm.getAnnexUrl());
        userLoginInfoService.add(form);
        //创建权限信息
        PermissionInfoForm permissionInfoForm = new PermissionInfoForm();
        permissionInfoForm.setUserId(studentInfoForm.getId());
        permissionInfoForm.setUserType("3");
        permissionInfoService.add(permissionInfoForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取考生详细信息", notes = "根据url的id来获取考生详细信息")
    @ApiImplicitParam(name = "id", value = "考生ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getUserBaseInfoById(@PathVariable("id") String id) {
        UserBaseInfo userBaseInfo = userBaseInfoService.get(id);
        //查询文件名称
        if (userBaseInfo.getAnnexUrl() != null) {
            AttachmentInfo attachmentInfo = attachmentInfoService.get(userBaseInfo.getAnnexUrl());
            if (attachmentInfo != null) {
                userBaseInfo.setAnnexName(attachmentInfo.getAttachName());
            } else {
                userBaseInfo.setAnnexName("");
            }
        }
        return new ResultBean(userBaseInfo);
    }

    @ApiOperation(value = "更新考生详细信息", notes = "根据url的id来指定更新对象，并根据传过来的studentInfo信息来更新考生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考生ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "studentInfoForm", value = "考生详细实体UserBaseInfoform", required = true, dataType = "UserBaseInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateUserBaseInfo(@PathVariable("id") String id, UserBaseInfoForm studentInfoform) {
        userBaseInfoService.update(studentInfoform);
        //修改用户登录信息
        userLoginInfoService.updateAccountByUserId(studentInfoform.getPhone(), id);
        //修改头像
        UserLoginInfoForm form = new UserLoginInfoForm();
        form.setUserId(id);
        form.setHeadImg(studentInfoform.getHeadImg());
        form.setPeopleType(studentInfoform.getPeopleType());
        form.setAnnexUrl(studentInfoform.getAnnexUrl());
        userLoginInfoService.updateLoginByUserId(form);
        return new ResultBean();
    }

    @ApiOperation(value = "删除考生", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "考生ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteUserBaseInfo(@PathVariable("id") String id) {
        //删除考生
        userBaseInfoService.delete(id);
        //删除考生登录信息
        return new ResultBean();
    }

    @Value("${uploadPath.win}")
    private String winUploadPath;
    @Value("${uploadPath.linux}")
    private String linuxUploadPath;

    public String getUploadPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return winUploadPath;
        } else {
            return linuxUploadPath;
        }
    }

    @ApiOperation(value = "导入", notes = "")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ResultBean exportInfo(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form) throws Exception {
        uploadService.addFile(file, form);
        List<Map<Integer, String>> list = ExcelUtils.readExcel(getUploadPath() + form.getFilePath(), 10);
        String[] columnName = {"*姓名", "*身份证号", "*所在企业", "*手机号", "*邮箱", "*专业", "*人员类型", "通讯地址", "邮政编码", "固定电话"};
        Map<Integer, String> m = list.get(0);
        StringBuffer sb = new StringBuffer("模板列名");
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            if (!columnName[i].equals(m.get(i))) {
                flag = true;
                sb.append("【" + columnName[i] + "】");
            }
        }
        sb.append("错误，请核对");
        if (flag) {
            return new ResultBean(1, sb.toString());
        }
        list.remove(0);
        Integer rightNum = 0;
        Integer errorNum = 0;
        UserBaseInfoForm userBaseInfoForm = new UserBaseInfoForm();
        List<Map<Integer, String>> errorList = new ArrayList<Map<Integer, String>>();
        for (Map<Integer, String> map : list) {
            //循环插入考生
            userBaseInfoForm.setId(UUID.randomUUID().toString().replace("-", ""));
            userBaseInfoForm.setRealName(map.get(0));
            userBaseInfoForm.setIdCard(map.get(1));
            userBaseInfoForm.setWorkUnit(map.get(2));
            userBaseInfoForm.setPhone(map.get(3));
            userBaseInfoForm.setEmail(map.get(4));
            userBaseInfoForm.setAddress(map.get(5));
            userBaseInfoForm.setPostCode(map.get(6));
            userBaseInfoForm.setFixedTelephone(map.get(7));
            //校验格式
            List<String> msgs = ValidateUtil.validate(userBaseInfoForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //查询身份证号
            UserBaseInfoQuery query1 = new UserBaseInfoQuery();
            query1.setIdCard(userBaseInfoForm.getIdCard());
            List<UserBaseInfo> list1 = userBaseInfoService.find(query1);
            if (list1.size() > 0) {
                msg.append("身份证号已存在,");
                return new ResultBean(0, "导入失败，含有已注册身份证号码:" + query1.getIdCard());
            }
            //查询手机号
            UserLoginInfoQuery query2 = new UserLoginInfoQuery();
            query2.setAccount(userBaseInfoForm.getPhone());
            query2.setAccountType(1);
            List<UserLoginInfo> list2 = userLoginInfoService.find(query2);
            if (list2.size() > 0) {
                msg.append("手机号已存在,");
                return new ResultBean(0, "导入失败，含有已注册手机号:" + query2.getAccount());
            }
        }
        for (Map<Integer, String> map : list) {
            //循环插入考生
            userBaseInfoForm.setId(UUID.randomUUID().toString().replace("-", ""));
            userBaseInfoForm.setRealName(map.get(0));
            userBaseInfoForm.setIdCard(map.get(1));
            userBaseInfoForm.setWorkUnit(map.get(2));
            userBaseInfoForm.setPhone(map.get(3));
            userBaseInfoForm.setEmail(map.get(4));
            userBaseInfoForm.setAddress(map.get(5));
            userBaseInfoForm.setPostCode(map.get(6));
            userBaseInfoForm.setFixedTelephone(map.get(7));
            userBaseInfoForm.setPeopleType1(map.get(8));
            userBaseInfoForm.setPeopleType2(map.get(9));
            //校验格式
            List<String> msgs = ValidateUtil.validate(userBaseInfoForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //查询身份证号
            UserBaseInfoQuery query1 = new UserBaseInfoQuery();
            query1.setIdCard(userBaseInfoForm.getIdCard());
            List<UserBaseInfo> list1 = userBaseInfoService.find(query1);
            if (list1.size() > 0) {
                msg.append("身份证号已存在,");
                return new ResultBean(0, "导入失败，含有已注册身份证号码:" + query1.getIdCard());
            }
            //查询手机号
            UserLoginInfoQuery query2 = new UserLoginInfoQuery();
            query2.setAccount(userBaseInfoForm.getPhone());
            query2.setAccountType(1);
            List<UserLoginInfo> list2 = userLoginInfoService.find(query2);
            if (list2.size() > 0) {
                msg.append("手机号已存在,");
                return new ResultBean(0, "导入失败，含有已注册手机号:" + query2.getAccount());
            }
            //判断状态
            if (msg.length() > 0) {
                map.put(10, msg.toString());
                errorNum++;
                errorList.add(map);
            } else {
                userBaseInfoService.add(userBaseInfoForm);
                //创建用户登录信息
                UserLoginInfoForm form1 = new UserLoginInfoForm();
                form1.setAccount(userBaseInfoForm.getPhone());
                form1.setAccountType(1);
                form1.setUserId(userBaseInfoForm.getId());
                form1.setHeadImg(userBaseInfoForm.getHeadImg());
                userLoginInfoService.add(form1);
                //创建权限信息
                PermissionInfoForm permissionInfoForm = new PermissionInfoForm();
                permissionInfoForm.setUserId(userBaseInfoForm.getId());
                permissionInfoForm.setUserType("3");
                permissionInfoService.add(permissionInfoForm);
                rightNum++;
            }
        }
        return new ResultBean(0, "导入成功【" + rightNum + "】个考生，失败【" + errorNum + "】个", errorList);
    }
}

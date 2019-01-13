package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.form.QuestionJudgeInfoForm;
import com.zpkj.exam.domain.form.SpecialExamineeInfoForm;
import com.zpkj.exam.domain.query.SpecialExamineeInfoQuery;
import com.zpkj.exam.service.SpecialExamineeInfoService;
import com.zpkj.exam.service.UploadService;
import com.zpkj.exam.service.impl.UploadServiceImpl;
import com.zpkj.exam.util.ExcelUtils;
import com.zpkj.exam.util.ValidateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/special")
@CrossOrigin
public class SpecialExamineeController {

    @Autowired
    SpecialExamineeInfoService specialExamineeService;
    @Autowired
    UploadService uploadService;

    @ApiOperation(value = "获取特殊考生列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getSpecialExamineeInfoList(@ModelAttribute SpecialExamineeInfoQuery query) {
        return new ResultBean(specialExamineeService.findByPage(query));
    }

    @ApiOperation(value = "创建特殊考生", notes = "创建特殊考生")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postSpecialExamineeInfo(SpecialExamineeInfoForm specialExamineeForm) {
        specialExamineeService.add(specialExamineeForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取特殊考生详细信息", notes = "根据url的id来获取特殊考生详细信息")
    @ApiImplicitParam(name = "id", value = "特殊考生ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getSpecialExamineeInfoById(@PathVariable("id") String id) {
        return new ResultBean(specialExamineeService.get(id));
    }

    @ApiOperation(value = "更新特殊考生详细信息", notes = "根据url的id来指定更新对象，并根据传过来的specialExaminee信息来更新特殊考生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "特殊考生ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "specialExamineeForm", value = "特殊考生详细实体SpecialExamineeInfoform", required = true, dataType = "SpecialExamineeInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateSpecialExamineeInfo(@PathVariable("id") String id, SpecialExamineeInfoForm specialExamineeform) {
        specialExamineeService.update(specialExamineeform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除特殊考生", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "特殊考生ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteSpecialExamineeInfo(@PathVariable("id") String id) {
        specialExamineeService.delete(id);
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
    public ResultBean exportQuestionJudgeInfo(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form) throws Exception {
        uploadService.addFile(file, form);
        List<Map<Integer, String>> list = ExcelUtils.readExcel(getUploadPath() + form.getFilePath(), 3);
        String[] columnName = {"姓名", "身份证号", "手机号"};
        Map<Integer, String> m = list.get(0);
        StringBuffer sb = new StringBuffer("模板列名");
        boolean flag = false;
        for (int i = 0; i < 3; i++) {
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
        SpecialExamineeInfoForm specialExamineeInfoForm = new SpecialExamineeInfoForm();
        List<Map<Integer, String>> errorList = new ArrayList<Map<Integer, String>>();
        for (Map<Integer, String> map : list) {
            //循环插入特殊考生
            specialExamineeInfoForm.setId(UUID.randomUUID().toString().replace("-", ""));
            specialExamineeInfoForm.setRealName(map.get(0));
            specialExamineeInfoForm.setIdCard(map.get(1));
            specialExamineeInfoForm.setPhone(map.get(2));
            //校验格式
            List<String> msgs = ValidateUtil.validate(specialExamineeInfoForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //判断状态
            if (msg.length() > 0) {
                map.put(3, msg.toString());
                errorNum++;
                errorList.add(map);
            } else {
                specialExamineeService.add(specialExamineeInfoForm);
                rightNum++;
            }
        }
        return new ResultBean(0, "导入成功【" + rightNum + "】个特殊考生，失败【" + errorNum + "】个", errorList);
    }
}

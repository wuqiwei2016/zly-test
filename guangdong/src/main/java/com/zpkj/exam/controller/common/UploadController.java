package com.zpkj.exam.controller.common;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanshipeng on 2017/3/17.
 */
@Controller
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 文件上传
     */
    @ApiOperation(value = "上传", notes = "")
    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResultBean uploadFile(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form) throws Exception {
        return new ResultBean(0, uploadService.addFile(file, form));
    }

    /**
     * 文件上传
     */
    @ResponseBody
    @RequestMapping(value = "/uploadUEditorFile", method = RequestMethod.POST)
    public Map uploadUEditorFile(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form) throws Exception {
        String id = uploadService.addFile(file, form);
        Map map = new HashMap();
        map.put("state", "SUCCESS");
        map.put("url", "/admin/upload/showImage?id=" + id);
        map.put("title", "");
        map.put("original", "");
        return map;
    }

    /**
     * 文件上传
     */
    @ResponseBody
    @RequestMapping(value = "/uploadLayUIFile", method = RequestMethod.POST)
    public Map uploadLayUIFile(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form) throws Exception {
        String id = uploadService.addFile(file, form);
        Map map = new HashMap();
        Map map2 = new HashMap();
        map.put("code", 0);
        map.put("msg", "");
        map2.put("src", "http://api.zp-kj.com/upload/showImage?id=" + id);
        map2.put("title", "");
        map.put("data", map2);
        return map;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(final String id, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.getDownload(id, response);
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    public void showImage(final String id, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.showImage(id, response);
    }

    @RequestMapping(value = "/showImageOne", method = RequestMethod.GET)
    public void showImageOne(final String id, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.showImageOne(id, response);
    }

    @RequestMapping(value = "/showImagetwo", method = RequestMethod.GET)
    public void showImageTwo(final String id, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.showImageTwo(id, response);
    }

    @RequestMapping(value = "/showImageThree", method = RequestMethod.GET)
    public void showImageThree(final String id, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.showImageThree(id, response);
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/downloadByPid", method = RequestMethod.GET)
    public void downloadByPid(final String pid, final HttpServletResponse response) throws FileNotFoundException {
        uploadService.getDownloadByPid(pid, response);
    }

    /**
     * 文件删除
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResultBean deleteFile(final String id) throws Exception {
        uploadService.deleteFile(id);
        return new ResultBean();
    }

    /**
     * 文件删除
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFileByPid", method = RequestMethod.POST)
    public ResultBean deleteFileByPid(final String pid) throws Exception {
        uploadService.deleteFileByPid(pid);
        return new ResultBean();
    }
}

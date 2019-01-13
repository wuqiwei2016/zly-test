package com.zpkj.exam.service;


import com.zpkj.exam.domain.form.AttachmentInfoForm;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanshipeng on 2017/3/16.
 */
public interface UploadService {
    /**
     * 文件上传
     */
    public String addFile(MultipartFile uploadItem, AttachmentInfoForm form) throws Exception;

    public boolean deleteFile(String id);

    public boolean deleteFileByPid(String pid);

    public void getDownload(String id, HttpServletResponse rsp);

    public void showImage(String id, HttpServletResponse rsp);

    public void getDownloadByPid(String pid, HttpServletResponse rsp);

    void showImageOne(String id, HttpServletResponse response);

    void showImageTwo(String id, HttpServletResponse response);

    void showImageThree(String id, HttpServletResponse response);
}

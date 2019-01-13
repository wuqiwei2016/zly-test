package com.zpkj.exam.service.impl;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zpkj.exam.dao.AttachmentInfoDao;
import com.zpkj.exam.dao.ExamInfoDao;
import com.zpkj.exam.dao.UserBaseInfoDao;
import com.zpkj.exam.dao.UserLoginInfoDao;
import com.zpkj.exam.domain.AttachmentInfo;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.UserLoginInfo;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.query.AttachmentInfoQuery;
import com.zpkj.exam.service.UploadService;
import com.zpkj.exam.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Created by wanshipeng on 2017/3/16.
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Value("${uploadPath.win}")
    private String winUploadPath;
    @Value("${uploadPath.linux}")
    private String linuxUploadPath;
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Autowired
    public AttachmentInfoDao attachmentDao;
    @Autowired
    private ExamInfoDao examInfoDao;
    public String getUploadPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return winUploadPath;
        } else {
            return linuxUploadPath;
        }
    }

    /**
     * 文件上传
     *
     * @throws Exception
     */
    public String addFile(MultipartFile file, AttachmentInfoForm form) throws Exception {
        //获取图片的大小 大于1M压缩 小于1M不做处理
        //file.transferTo(File dest);
//        CommonsMultipartFile cf= (CommonsMultipartFile)file;
//        DiskFileItem fi = (DiskFileItem)cf.getFileItem();
//        File f = fi.getStoreLocation();
        String originalFilename = file.getOriginalFilename();   //原始文件名
        String suffix = FileUtil.getSuffix(originalFilename);   //文件后缀名
        String attachPath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/")) + UUID.randomUUID().toString();
        String realPath = getUploadPath() + attachPath;
        // ****上传文件****
        // 上传路径://真实路径/文件上传路径/日期/时间.后缀
        String targetPathNameWithoutSuffix = realPath;
        String path = attachPath + "." + suffix;
        if (suffix.equals("jpg") || suffix.equals("JPG") || suffix.equals("PNG") || suffix.equals("png") || suffix.equals("JPEG") || suffix.equals("jpeg")){
            File f = null;
            if(file.equals("")||file.getSize()<=0){
                file = null;
            }else {
                InputStream ins = file.getInputStream();
                f = new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
            }
            long length = f.length();
            //此时得到的结果是B 转化为M
            long l = length / 1000000;

            LOGGER.info("该图片上传路径为【打印该日志测试 与大于1M的图片新路径是否一致】"+path);
            if (l>=1){
                //开始进行压缩并且上传
                FileUtil.uploadFileSpringMVC(file, getUploadPath()+attachPath + suffix);
                LOGGER.info("该图片为1M以上 图片路径为："+targetPathNameWithoutSuffix + "." + FileUtil.getSuffix(file.getOriginalFilename()));
                LOGGER.info("该图片开始进行压缩 压缩路径为"+targetPathNameWithoutSuffix + "." + FileUtil.getSuffix(file.getOriginalFilename()));
                Boolean aBoolean = zipWidthHeightImageFile(getUploadPath()+attachPath+ suffix+"."+suffix, targetPathNameWithoutSuffix + "." + FileUtil.getSuffix(file.getOriginalFilename()), 500, 500, 0.6f);
                //LOGGER.info("上传压缩后的图片路径为");
                if(aBoolean==true){
                    FileUtil.delete(new File(getUploadPath()+attachPath+ suffix+"."+suffix));
                }
            }else{
                FileUtil.uploadFileSpringMVC(file, targetPathNameWithoutSuffix);
            }
            FileUtil.delete(new File(file.getOriginalFilename()));
        }else{
            FileUtil.uploadFileSpringMVC(file, targetPathNameWithoutSuffix);
        }

        //保存文件
        form.setAttachName(originalFilename);
        form.setFileType(suffix);
        form.setFileLength((int) file.getSize());
        form.setFilePath(path);
        attachmentDao.add(form);
        if (form.getPeopleType()!=null){
            return form.getId()+","+form.getAttachName();
        }
        return form.getId();
    }

    public boolean deleteFile(String id) {
        AttachmentInfo attachment = attachmentDao.get(id);
        attachmentDao.delete(id);
        File file = new File(getUploadPath() + attachment.getFilePath());
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    public boolean deleteFileByPid(String pid) {
        AttachmentInfoQuery query = new AttachmentInfoQuery();
        query.setOwnerId(pid);
        List<AttachmentInfo> list = attachmentDao.find(query);
        for (AttachmentInfo attachment : list) {
            attachmentDao.delete(attachment.getId());
            File file = new File(getUploadPath() + attachment.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        }
        return true;
    }

    public void getDownload(String id, HttpServletResponse rsp) {
        try {
            AttachmentInfo attachment = attachmentDao.get(id);
            File file = null;
            if (attachment != null) {
                file = new File(getUploadPath() + attachment.getFilePath());
            }
            if (file != null && file.exists()) {
                FileUtil.download(file.getAbsolutePath(), attachment.getAttachName().replace("." + attachment.getFileType(), ""), rsp);
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImage(String id, HttpServletResponse rsp) {
        try {
            AttachmentInfo attachment = attachmentDao.get(id);
            File file = null;
            if (attachment != null) {
                file = new File(getUploadPath() + attachment.getFilePath());
            }
            if (file != null && file.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, attachment.getFileType(), rsp.getOutputStream());
                } else {
                    FileUtil.download(file.getAbsolutePath(), attachment.getAttachName().replace("." + attachment.getFileType(), ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看抓拍图片
     * @param id
     * @param rsp
     */
    public void showCaptureImage(String id, HttpServletResponse rsp) {
        try {
            ExamInfo examInfo = examInfoDao.get(id);
            File file = null;
            File file2 = null;
            File file3 = null;
            if (examInfo != null) {
                file = new File(getUploadPath() + examInfo.getPicture1());
                file2 = new File(getUploadPath() + examInfo.getPicture2());
                file3 = new File(getUploadPath() + examInfo.getPicture3());
            }
            if (file != null && file.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "jpg", rsp.getOutputStream());
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture1().replace(".jpg" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
            if (file2 != null && file2.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "jpg", rsp.getOutputStream());
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture2().replace(".jpg" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
            if (file3 != null && file3.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "jpg", rsp.getOutputStream());
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture3().replace(".jpg" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showImageOne(String id, HttpServletResponse rsp) {
        LOGGER.info("执行查看第一张图片，参数为"+id);
        try {
            ExamInfo examInfo = examInfoDao.get(id);
            LOGGER.info("查询ExamInfo对象，返回结果为："+examInfo.toString());
            File file = null;
            if (examInfo != null) {
                LOGGER.info("examInfo不为空");
                file = new File(getUploadPath() + examInfo.getPicture1());
                LOGGER.info("file开始读取");
            }
            if (file != null && file.exists()) {
                LOGGER.info("file不为空");
                BufferedImage image = ImageIO.read(file);
                LOGGER.info("image读取成功");
                if (image != null) {
                    LOGGER.info("image不为空存在 开始执行查看");
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "png", rsp.getOutputStream());
                    LOGGER.info("查看第一张图片成功");
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture1().replace(".png" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showImageTwo(String id, HttpServletResponse rsp) {
        LOGGER.info("执行查看第二张图片，参数为"+id);
        try {
            ExamInfo examInfo = examInfoDao.get(id);
            File file = null;
            if (examInfo != null) {
                file = new File(getUploadPath() + examInfo.getPicture2());
            }
            if (file != null && file.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "png", rsp.getOutputStream());
                    LOGGER.info("查看第二张图片成功");
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture2().replace(".png" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImageThree(String id, HttpServletResponse rsp) {
        LOGGER.info("执行查看第三张图片，参数为"+id);
        try {
            ExamInfo examInfo = examInfoDao.get(id);
            File file = null;
            if (examInfo != null) {
                file = new File(getUploadPath() + examInfo.getPicture3());
            }
            if (file != null && file.exists()) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    rsp.setContentType("image/jpeg");
                    ImageIO.write(image, "png", rsp.getOutputStream());
                    LOGGER.info("查看第三张图片成功");
                } else {
                    FileUtil.download(file.getAbsolutePath(), examInfo.getPicture3().replace(".png" , ""), rsp);
                }
            } else {
                rsp.setContentType("text/html;charset=UTF-8");
                rsp.getWriter().write("系统错误或文件已被删除！请联系管理员！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDownloadByPid(String pid, HttpServletResponse rsp) {
        AttachmentInfoQuery query = new AttachmentInfoQuery();
        query.setOwnerId(pid);
        List<AttachmentInfo> list = attachmentDao.find(query);
        if (list.size() > 0) {
            getDownload(list.get(0).getId(), rsp);
        }
    }
    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***
     * 压缩制定大小图片
     *
     * @param oldPath  临时图片路径
     * @param copyPath 压缩图片保存路径
     * @param width    宽度
     * @param height   高度
     * @param quality  高清度
     * @return
     * @throws Exception
     */
    private Boolean zipWidthHeightImageFile(String oldPath, String copyPath, int width, int height,
                                            float quality) {
        Boolean sta = false;
        File oldFile = new File(oldPath);
        File newFile = new File(copyPath);
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);

            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
            sta = true;
        } catch (Exception e) {
            e.printStackTrace();
            sta = false;
        }
        return sta;
    }
}

package com.zpkj.exam.util;


import jodd.io.FileNameUtil;
import jodd.io.NetUtil;
import jodd.io.findfile.FindFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.UUID;

/**
 * 文件工具
 * Created by Administrator on 2018/5/22.
 */
public class FileUtil {

    public static final int BUFFERSIZE = 2048;

    private FileUtil() {
        throw new Error("工具类不能被实例化");
    }

    public static void createParentDirectorys(String path) throws IOException {
        new File(getFilePath(path)).mkdirs();
    }

    public static void downloadFileFromUrl(String url, File targetFile) throws IOException {
        NetUtil.downloadFile(url, targetFile);
    }

    public static void uploadFile(File upload, String targetPathNameWithoutSuffix) {
        try {
            File file = new File(targetPathNameWithoutSuffix + "." + getSuffix(upload.getName()));
            createParentDirectorys(targetPathNameWithoutSuffix + "." + getSuffix(upload.getName()));
            FileUtils.copyFile(upload, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFileSpringMVC(MultipartFile file, String targetPathNameWithoutSuffix) throws Exception {
        if (!file.isEmpty()) {
            try {
                BufferedInputStream br = new BufferedInputStream(
                        file.getInputStream());
                int tag;
                //写文件
                File file1 = new File(targetPathNameWithoutSuffix + "." + getSuffix(file.getOriginalFilename()));
                //判断目录是否存在
                if (!file1.getParentFile().exists()) {
                    //不存在创建目录
                    file1.getParentFile().mkdirs();
                }
                BufferedOutputStream pw = new BufferedOutputStream(new FileOutputStream(file1));
                while ((tag = br.read()) != -1) {
                    pw.write(tag);
                }
                pw.flush();
                pw.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void uploadFilesSpringMVC(HttpServletRequest request, String targetPath) throws IllegalStateException, IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                if (file != null) {
                    String myFileName = file.getOriginalFilename();
                    if (myFileName != null && !myFileName.equals("")) {
                        String path = targetPath + file.getOriginalFilename();
                        File localFile = new File(path);
                        file.transferTo(localFile);
                    }
                }
            }
        }
    }

    public static void delete(File destFile) throws IOException {
        jodd.io.FileUtil.delete(destFile);
    }

    public static void cleanDir(File destDir) throws IOException {
        jodd.io.FileUtil.cleanDir(destDir);
    }

    public static boolean renameFile(String resFilePath, String newFileName) {
        String newFilePath = formatPath(getFilePath(resFilePath) + newFileName);
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    public static long genFileSize(String distFilePath) {
        File distFile = new File(distFilePath);
        if (distFile.isFile()) {
            return distFile.length();
        }
        if (distFile.isDirectory()) {
            return FileUtils.sizeOfDirectory(distFile);
        }
        return -1L;
    }

    public static String[] listFilebySuffix(String folder, String suffix) {
        IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
        IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
        FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);
        return new File(folder).list(filenameFilter);
    }

    public static Iterator<File> findFile(String folder, boolean recursive, boolean includeDirs) {
        return new FindFile().setRecursive(recursive).setIncludeDirs(includeDirs).searchPath(folder).iterator();
    }

    /* Error */
    public static void writeFileToBrowser(InputStream inputStream, javax.servlet.http.HttpServletResponse response) {


    }

    /* Error */
    public static void download(String path, String targetNameWithoutSuffix, javax.servlet.http.HttpServletResponse response) throws FileNotFoundException {
        // 清空response
        response.reset();
        // 设置response的编码方式
        response.setContentType("application/x-msdownload");
        // 写明要下载的文件的大小
        response.setContentLength((int) new File(path).length());
        try {
            String targetName2 = URLEncoder.encode(targetNameWithoutSuffix + "." + FileUtil.getSuffix(path), "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + targetName2);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        byte[] buff = new byte[BUFFERSIZE];
        int len = -1;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(path)));
            bos = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            while ((len = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileType(String suffix) {
        if (suffix != null) {
            if ((suffix.equals("jpg")) || (suffix.equals("png")) || (suffix.equals("gif")) || (suffix.equals("bmp"))) {
                return "img";
            }
            if ((suffix.equals("mp4")) || (suffix.equals("wmv")) || (suffix.equals("avi")) || (suffix.equals("flv")) || (suffix.equals("rm")) || (suffix.equals("rmvb")) || (suffix.equals("mkv"))) {
                return "video";
            }
            if ((suffix.equals("mp3")) || (suffix.equals("wav")) || (suffix.equals("midi"))) {
                return "audio";
            }
            if ((suffix.equals("doc")) || (suffix.equals("docx")) || (suffix.equals("xls")) || (suffix.equals("xlsx")) || (suffix.equals("ppt")) || (suffix.equals("ppts")) || (suffix.equals("txt")) || (suffix.equals("wps")) || (suffix.equals("et")) || (suffix.equals("dps"))) {
                return "doc";
            }
            if ((suffix.equals("rar")) || (suffix.equals("zip")) || (suffix.equals("tar")) || (suffix.equals("tgz")) || (suffix.equals("gz"))) {
                return "rar";
            }
            return "other";
        }
        return "other";
    }

    public static String getSuffix(String fileName) {
        return FileNameUtil.getExtension(fileName);
    }

    public static String getFileNameWithoutSuffixByPath(String path) {
        return FileNameUtil.getBaseName(path);
    }

    public static String getFileNameWithSuffixByPath(String path) {
        return FileNameUtil.getName(path);
    }

    public static String getFilePath(String path) {
        return FileNameUtil.getFullPath(path);
    }

    public static String getFilePathNoEndSeparator(String path) {
        return FileNameUtil.getFullPathNoEndSeparator(path);
    }

    public static String getFileRelativePath(String path, String basePath) {
        return path.replaceFirst(basePath, "");
    }

    public static String formatPath(String path) {
        return FileNameUtil.normalize(path);
    }

}

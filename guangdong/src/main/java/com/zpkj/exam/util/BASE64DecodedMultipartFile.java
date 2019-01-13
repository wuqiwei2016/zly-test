package com.zpkj.exam.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by 姚洪强 on 2018/7/22.
 */
public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + header.split("/")[1];
    }

    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    public long getSize() {
        return imgContent.length;
    }

    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}

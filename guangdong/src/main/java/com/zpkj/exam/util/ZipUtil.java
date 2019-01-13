package com.zpkj.exam.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrator on 2018/5/22.
 */
public class ZipUtil {

    public static void main(String[] args) throws Exception {
        try {
            readZipFile("D:\\ztree.zip");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                long size = ze.getSize();
                if (size > 0) {
                    BufferedInputStream br = new BufferedInputStream(
                            zf.getInputStream(ze));
                    int tag;
                    //写文件
                    File file1 = new File("D:/abc/"+ze.getName());
                    //判断目录是否存在
                    if(!file1.getParentFile().exists()) {
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
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }
}

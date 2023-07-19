package cn.iocoder.yudao.module.fis.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.*;

/**
 * ftp工具类
 */
public class FtpUtil {


    /**
     * 文件上传ftp
     *
     * @param path 路径
     * @param file 文件
     * @throws IOException 异常
     */
    public static void upload(String path, File file) throws IOException {
        Ftp ftp = new Ftp("172.26.37.69", 21, "ftpadmin", "Osc_12345");
        ftp.upload(path, file);
        ftp.close();
    }

    /**
     * MultipartFile上传传ftp
     *
     * @param path  路径
     * @param mFile MultipartFile
     * @throws IOException 异常
     */
    public static void uploadFromMultipartFile(String path, MultipartFile mFile) throws IOException {
        if (null == mFile) return;
        Ftp ftp = new Ftp("172.26.37.69", 21, "ftpadmin", "Osc_12345");
        if (!ftp.exist(path)) {
            ftp.mkdir(path);
        }
        ftp.upload(path, mFile.getOriginalFilename(), mFile.getInputStream());
        ftp.close();
    }

    public static byte[] download(String path) throws IOException {
        Ftp ftp = new Ftp("172.26.37.69", 21, "ftpadmin", "Osc_12345");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftp.download(StrUtil.subBefore(path,StrUtil.SLASH,true), FileNameUtil.getName(path), outputStream);
        ftp.close();
        return outputStream.toByteArray();
    }
}

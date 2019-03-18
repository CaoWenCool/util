package com.demo.fileutils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 18:36
 * @version: V1.0
 * @detail: 文件工具类
 **/
public class FileUtil {

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Calendar cale = Calendar.getInstance();

    //文件读取字符串
    public static String readFromFile(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        int size = in.available();
        byte[] buffer = new byte[size];
        in.read(buffer);
        in.close();
        return new String(buffer, "UTF-8");
    }

    /**
     * Response 下载流构建
     * @param fileRealPath
     * @param request
     * @param response
     */
    public static void downloadFile(String fileRealPath, HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = new File(fileRealPath);
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            // 设置文件MIME类型
            response.setContentType(request.getSession().getServletContext().getMimeType(file.getName()));
            System.out.println(request.getSession().getServletContext().getMimeType(file.getName()));
            // 设置Content-Disposition
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            int end = 0;
            byte[] read = new byte[1024];
            while ((end = in.read(read)) != -1) {
                out.write(read, 0, end);
            }
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String uploadFile(MultipartFile multipartFile, String realPath){
        try {
            String name = getDateTime("yyyyMMddHHmmss",new Date()) + "_" + multipartFile.getOriginalFilename();
            if (!new File(realPath).exists()) {
                new File(realPath).mkdirs();
            }
            File file = new File(realPath + File.separator + name);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out;
            out = new FileOutputStream(file);
            InputStream in = multipartFile.getInputStream();
            int reader = 0;
            byte[] buffer = new byte[1024];
            while ((reader = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, reader);
            }
            if (out != null) out.close();
            if (in != null) in.close();
            return name;
        }catch (IOException e) {
            return null;
        }

    }

    /**
     * 将日期类转换成指定格式的字符串形式
     */
    private static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            System.out.println("aDate is null");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    public static String uploadFile(HttpServletRequest request, String realPath) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile multipartFile = multipartRequest.getFile("file");
        return uploadFile(multipartFile, realPath);
    }

    /**
     * 流装换成字符串
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

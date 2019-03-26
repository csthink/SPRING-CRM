package com.csthink.crm.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileUploadUtils {

    public static Map<String, String> upload(HttpServletRequest request) throws Exception {
        // 定义一个Map集合用于保存接收到的数据:
        Map<String, String> resultMap = new HashMap<>();
        // 获取文件上传路径,将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = request.getServletContext().getRealPath("/WEB-INF/upload");
        // 上传时生成的临时文件保存目录
//        String tempPath = request.getServletContext().getRealPath("/WEB-INF/temp");
        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }

        InputStream is = null;
        FileOutputStream fos = null;
        try {
            String saveName = ""; // 上传文件最终保存的完整名称
            String message; //

            // 1. 创建一个磁盘文件项工厂对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

            // 以下两项为可选设置，设置时配合之前配置的 tempPath 使用
            // 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
//            diskFileItemFactory.setSizeThreshold(1024 * 100);
            // 设置上传时生成的临时文件的保存目录
//            diskFileItemFactory.setRepository(file);

            // 2. 创建一个文件上传解析器
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            // 解决上传文件名的中文乱码
            fileUpload.setHeaderEncoding("UTF-8");
            // 监听文件上传进度
//            fileUpload.setProgressListener(new ProgressListener() {
//                public void update(long pBytesRead, long pContentLength, int arg2) {
//                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
//                }
//            });

            /*
            // 判断提交上来的数据是否是上传表单的数据
            if (!fileUpload.isMultipartContent(request)) {
                // 按照传统方式获取数据
                return;
            }
            */

            // 设置上传单个文件的大小的最大值，目前设置为1024 * 1024字节，也就是1MB
            fileUpload.setFileSizeMax(1024 * 1024);
            // 设置上传文件总量的最大值，最大值 = 同时上传的多个文件的大小的最大值的和，目前设置为10MB
            fileUpload.setSizeMax(1024 * 1024 * 10);

            // 3. 使用 ServletFileUpload 解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> fileItemList = fileUpload.parseRequest(request);

            // 4.遍历集合，获得每个FileItem，判断是表单项还是文件上传项
            // 如果fileItem 中封装的是普通表单项
            for (FileItem fileItem : fileItemList)
                if (fileItem.isFormField()) {
                    // 普通表单项,接收表单项参数的值
                    String fieldName = fileItem.getFieldName(); //  获得表单项的name属性的值
                    String fieldValue = fileItem.getString("UTF-8"); // 获得表单项的值
                    // 将表单数据保存到map中
                    resultMap.put(fieldName, fieldValue);
                } else {
                    // 文件上传项,文件上传功能
                    /*
                     注意事项:
                     1. 为保证服务器安全，上传文件应该放在外界无法直接访问的目录下，比如放于WEB-INF目录下。
                     2. 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名。
                     3. 为防止一个目录下面出现太多文件，要使用hash算法打散存储。
                     4. 要限制上传文件的最大值。
                     5. 要限制上传文件的类型，在收到上传文件名时，判断后缀名是否合法。
                     */
                    String fileName = fileItem.getName(); // 获得文件上传的名称
                    if (null == fileName || fileName.trim().equals("")) {
                        continue;
                    }

                    // 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    // 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
                    System.out.println("用户上传的原文件名: " + fileName);

                    // 得到上传文件的扩展名
                    String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
                    String[] validExt = {"jpg", "jpeg", "gif", "bmp", "png"};
                    // 判断上传的文件类型是否合法
                    if (!ArrayUtils.contains(validExt, fileExtName)) {
                        throw new Exception("上传文件的类型不合法");
                    }

                    // 获取 fileItem 中上传文件的输入流
                    is = fileItem.getInputStream();

                    // 通过工具类获得唯一文件名
                    String newFileName = getUUIDFileName(fileName);
                    System.out.println("UUID " + newFileName);
                    saveName = savePath + File.separator + newFileName; // 保存的文件名

                    System.out.println("上传的文件: " + saveName);
                    // 创建一个文件输出流
                    fos = new FileOutputStream(saveName);
                    // 创建一个缓冲区
                    byte[] buffer = new byte[1024];
                    // 判断输入流中的数据是否已经读完的标识
                    int length = 0;
                    // 循环将输入流读入到缓冲区当中
                    while ((length = is.read(buffer)) != -1) {
                        // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        fos.write(buffer, 0, length);
                    }

                    // 文件上传成功
                    // 删除处理文件上传时生成的临时文件
                    fileItem.delete();
                    resultMap.put("fileName", newFileName);
                }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            throw new Exception("单个文件超出最大值");
        } catch (FileUploadBase.SizeLimitExceededException e) {
            throw new Exception("上传文件的总的大小超出限制的最大值");
        } finally {
            try {
                if (null != is) {
                    is.close(); // 关闭输入流
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                if (null != fos) {
                    fos.close(); // 关闭输出流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultMap;
    }


    private static String getUUIDFileName(String fileName) {
        // 将文件名的前面部分进行截取：xx.jpg   --->   .jpg
        int idx = fileName.lastIndexOf(".");
        String ext = fileName.substring(idx);

        return UUID.randomUUID().toString().replace("-", "") + ext;
    }

}

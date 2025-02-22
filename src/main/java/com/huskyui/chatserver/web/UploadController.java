package com.huskyui.chatserver.web;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.utils.IdGenUtils;
import com.huskyui.chatserver.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Resource
    private UploadUtils uploadUtils;

    @Value("${upload.location}")
    private String uploadLocation;

    @RequestMapping("/image")
    public Result uploadImage(@RequestParam MultipartFile file) {
        try {
            String imgUrl = uploadUtils.updateImage("test-bucket", IdGenUtils.id(), file.getInputStream());
            if (StringUtils.isNotEmpty(imgUrl)){
                return Result.ok(imgUrl);
            }
        }catch (Exception e){
            log.error("upload file error !",e);
        }
        return Result.fail(1001,"上传失败");
    }

    @RequestMapping("/getImage")
    public void getImage(@RequestParam("fileName")String fileName,HttpServletResponse response) throws IOException {
        String filePath = uploadLocation+fileName;
        File file = new File(filePath);
        // 设置正确的MIME类型
        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType == null) {
            mimeType = "image/jpeg"; // 默认类型
        }
        response.setContentType(mimeType);
        response.setContentType(mimeType);
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            // 处理异常（如客户端断开连接）
            log.error("Error sending image", e);
        }
    }


}

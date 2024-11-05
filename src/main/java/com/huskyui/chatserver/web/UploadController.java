package com.huskyui.chatserver.web;

import com.huskyui.chatserver.model.Result;
import com.huskyui.chatserver.utils.IdGenUtils;
import com.huskyui.chatserver.utils.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {

    @Resource
    private UploadUtils uploadUtils;

    @RequestMapping("/image")
    public Result uploadImage(@RequestParam MultipartFile file) {
        try {
            String imgUrl = uploadUtils.updateImage("test-bucket", IdGenUtils.id(), file.getInputStream());
            if (StringUtils.isEmpty(imgUrl)){
                return Result.ok(imgUrl);
            }
        }catch (Exception e){
            log.error("upload file error !",e);
        }
        return Result.fail(1001,"上传失败");
    }

}

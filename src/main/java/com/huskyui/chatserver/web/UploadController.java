package com.huskyui.chatserver.web;

import com.huskyui.chatserver.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/image")
    public Result uploadImage(@RequestParam MultipartFile file) {
        return
    }


}

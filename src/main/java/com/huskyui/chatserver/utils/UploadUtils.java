package com.huskyui.chatserver.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class UploadUtils {

    @Value("${upload.location}")
    private String uploadLocation;


    public String updateImage(String bucketName,String fileName,InputStream stream){
        try {
            FileUtils.copyInputStreamToFile(stream,new File(String.format("%s/%s/%s",uploadLocation,bucketName,fileName)));
            return String.format("/%s/%s",bucketName,fileName);
        }catch (Exception e){
            return null;
        }
    }


}

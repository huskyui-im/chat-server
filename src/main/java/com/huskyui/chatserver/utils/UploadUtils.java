package com.huskyui.chatserver.utils;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class UploadUtils {

    @Resource
    private MinioClient minioClient;

    public String updateImage(String bucketName,String fileName,InputStream stream){
        try {
            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .contentType("image/png")
                    .stream(stream, -1, 10485760)
                    .build());
            // todo 只返回bucketName+fileName;再提供一个图片地址的根目录（接口），防止以后切换
            return String.format("/%s/%s",bucketName,fileName);
        }catch (Exception e){
            return null;
        }
    }


}

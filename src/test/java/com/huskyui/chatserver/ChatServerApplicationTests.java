package com.huskyui.chatserver;

import com.huskyui.chatserver.utils.RemoteCacheUtils;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class ChatServerApplicationTests {

    @Resource
    private RemoteCacheUtils remoteCacheUtils;

    @Test
    public void testRedis(){
        List<String> objects = remoteCacheUtils.historyMessageList("mystream");
        System.out.println(objects);
    }

    @Resource
    private MinioClient minioClient;


    @Test
    public void testUpload() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream inputStream = new FileInputStream("/home/huskyui/图片/壁纸/672f973cecf341eba21b874362384bc6.jpg");
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket("test-bucket")
                .object("test-filename2")
                        .contentType("image/png")
                .stream(inputStream, -1, 10485760)
                .build());
        System.out.println(objectWriteResponse);
    }

//    @Test
//    public void testGetFileName() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//
//        minioClient.getPresignedObjectUrl()
//        System.out.println(objectWriteResponse);
//    }

}

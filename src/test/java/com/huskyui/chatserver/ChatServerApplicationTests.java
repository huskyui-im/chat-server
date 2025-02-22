package com.huskyui.chatserver;

import com.huskyui.chatserver.utils.RemoteCacheUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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


//    @Test
//    public void testGetFileName() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//
//        minioClient.getPresignedObjectUrl()
//        System.out.println(objectWriteResponse);
//    }

}

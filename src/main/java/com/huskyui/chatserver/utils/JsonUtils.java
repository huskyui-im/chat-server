package com.huskyui.chatserver.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();





    public static <T> T stringToObject(String json, TypeReference<T> typeReference) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeReference);
        }catch (Exception e){
            return null;
        }
    }

    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        }catch (Exception e){
            return null;
        }
    }

}

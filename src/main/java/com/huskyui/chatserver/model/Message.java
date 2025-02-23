package com.huskyui.chatserver.model;

import lombok.Data;

import java.util.Map;

@Data
public class Message {
    int opType;

    String group;

    String message;

    String sendUser;

    private Map<String,Object> extendData;

}

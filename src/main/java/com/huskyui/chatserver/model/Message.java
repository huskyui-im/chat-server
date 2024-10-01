package com.huskyui.chatserver.model;

import lombok.Data;

@Data
public class Message {
    int opType;

    String group;

    String msg;

}

package com.huskyui.chatserver.service;

import com.google.common.collect.ImmutableMap;
import com.huskyui.chatserver.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final static ImmutableMap<String/*token*/, User/*用户信息*/> tokenInfoMap =
            ImmutableMap.of(
                    "01cd7dc2939f61b5757238b1a8b4c918",new User("huskyui"),
                    "cfa8cee0785f251032f8c293e4cdb2f6",new User("zz")
            );


    public User getUserInfoByToken(String token) {
        return tokenInfoMap.getOrDefault(token, null);
    }


}

package com.huskyui.chatserver.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName chat_user
 */
public class ChatUser{
    /**
     * 
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 加密密码
     */
    private String encryptPassword;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 有效性 1有效 0 无效
     */
    private Integer rowStatus;

    /**
     * create_user
     */
    private String createUser;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String updateUser;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 加密密码
     */
    public String getEncryptPassword() {
        return encryptPassword;
    }

    /**
     * 加密密码
     */
    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    /**
     * 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 有效性 1有效 0 无效
     */
    public Integer getRowStatus() {
        return rowStatus;
    }

    /**
     * 有效性 1有效 0 无效
     */
    public void setRowStatus(Integer rowStatus) {
        this.rowStatus = rowStatus;
    }

    /**
     * create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * create_user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
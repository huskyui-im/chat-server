package com.huskyui.chatserver.domain;


/**
 * 
 * @TableName group_base_info
 */
public class GroupBaseInfo{
    /**
     * 
     */
    private Long id;

    /**
     * group name
     */
    private String name;

    /**
     * admin user id
     */
    private Long adminId;

    /**
     * group avatar
     */
    private String avatar;

    /**
     * 有效性
     */
    private Integer rowStatus;

    private static final long serialVersionUID = 1L;

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
     * group name
     */
    public String getName() {
        return name;
    }

    /**
     * group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * admin user id
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * admin user id
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * group avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * group avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 有效性
     */
    public Integer getRowStatus() {
        return rowStatus;
    }

    /**
     * 有效性
     */
    public void setRowStatus(Integer rowStatus) {
        this.rowStatus = rowStatus;
    }

}
package com.wowo.wowo.Model;

public class FriendListModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    private String name;
    private boolean ischeck;
    private String id;
    private String headurl;
}

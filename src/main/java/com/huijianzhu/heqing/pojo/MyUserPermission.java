package com.huijianzhu.heqing.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ================================================================
 * 说明：用户信息拓展类
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  11:39            创建
 * =================================================================
 **/
public class MyUserPermission {

    private String account; //用户账号
    private List<String> modelList=new ArrayList<>(); //存储当前用户用于的模块id信息
    private List<MyTree> permissionList=new ArrayList<>();  //当所以的模块信息


    public List<String> getModelList() {
        return modelList;
    }

    public void setModelList(String modelId) {
        String[] split = modelId.trim().split(",");
        Arrays.stream(split).forEach(e->{modelList.add(e);});
    }

    public List<MyTree> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<MyTree> permissionList) {
        this.permissionList = permissionList;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

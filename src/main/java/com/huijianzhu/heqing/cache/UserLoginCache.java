package com.huijianzhu.heqing.cache;

import com.huijianzhu.heqing.entity.TdUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================================
 * 说明：用户登录缓存
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:49            创建
 * =================================================================
 **/
@Component
@Data
public class UserLoginCache {
    private UserLoginCache(){}
    //创建一个在线用户信息缓存容器    key是token  value是用户信息
    private ConcurrentHashMap<String, TdUser> onlineUserCache=new ConcurrentHashMap<>();

    //创建一个在线用户账号信息缓存容器 key是账号   value是token
    private ConcurrentHashMap<String,String> onlineAccountCache=new ConcurrentHashMap<>();
}

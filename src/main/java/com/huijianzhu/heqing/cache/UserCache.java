package com.huijianzhu.heqing.cache;

import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.mapper.TdUserMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ================================================================
 * 说明：创建一个用户信息缓存
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:16            创建
 * =================================================================
 **/
@Component
@Data
public class UserCache {

    private UserCache(){}

    //创建一个存储用户信息缓存容器
    private ConcurrentHashMap<String,TdUser> userCache=new ConcurrentHashMap<>();

    //创建一个存储生产对应的账号缓存
    private ConcurrentHashMap<Integer,Integer> accountCache=new ConcurrentHashMap<>();

    //注入操作用户信息的mapper接口
    @Autowired
    private TdUserMapper tdUserMapper;


    @PostConstruct
    public void init(){
        //初始化当前容器
        refreshCache();
    }



    /**
     *  功能：将缓存中有效的用户信息存储到集合中
     *  时间：2020/4/30 10:46
     */
    public List<TdUser> getUserList(){
        return userCache.entrySet().stream().map(e->e.getValue()).collect(Collectors.toList());
    }

    /**
     *  功能：获取账号信息
     *  时间：2020/4/29 17:19
     *  返回：String
     *  描述：获取一个账号信息
     */
    public String getUserAccount() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Integer currDate = Integer.parseInt(simpleDateFormat.format(new Date()));
        Integer account;
        synchronized (UserLoginCache.class) {
            //获取accountCache容器是否有当前天对应的账号
            account = accountCache.get(currDate);
            account++;

            //将自增后信息重新存储到对应天下对应的缓存上
            accountCache.put(currDate, account);
        }
        return account.toString();
    }
    /**
     *  功能：刷新当前缓存容器
     *  时间：2020/4/30 10:33
     */
    public void refreshCache(){
        List<TdUser> tdUsers = tdUserMapper.selectUserAll();
        tdUsers.forEach(e->{
            userCache.put(e.getUserAccount(),e);
        });
    }
}

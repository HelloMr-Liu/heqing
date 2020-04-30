package com.huijianzhu.heqing.service.impl;

import com.huijianzhu.heqing.cache.UserCache;
import com.huijianzhu.heqing.cache.UserLoginCache;
import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.service.LoginService;
import com.huijianzhu.heqing.utils.MyUtils;
import com.huijianzhu.heqing.vo.SystemResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ================================================================
 * 说明：当前类说说明
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  14:19            创建
 * =================================================================
 **/
@Service
public class LoginServiceImpl implements LoginService {

    //注入对应的用户登录缓存信息
    @Autowired
    private UserLoginCache userLoginCache;

    //注入用户信息
    @Autowired
    private UserCache userCache;

    /**
     *  功能：用户登录
     *  时间：2020/4/30 14:16
     *  参数：名称            类型            描述
     *       userAccount    String         用户账号
     *       password       String         账号密码
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    public SystemResult login(String userAccount, String password){
        //获取用户信息
        TdUser tdUser = userCache.getUserCache().get(userAccount);
        //判断用户是否存在
        if(tdUser==null||!(tdUser.getPassWord().equals(MyUtils.getEncryption(password)))){
            return SystemResult.build(301,"用户名或密码输入错误...");
        }
        //判断当前用户是否已经在其他地方登录
        String onlineUserToken = userLoginCache.getOnlineAccountCache().get(userAccount);
        if(StringUtils.isNotBlank(onlineUserToken)){
            //顶替掉原在线用户信息
            userLoginCache.getOnlineUserCache().remove(onlineUserToken);
            userLoginCache.getOnlineAccountCache().remove(userAccount);
        }
        //获取一个唯一Id Token
        String loginToken=MyUtils.get32LengthUUID();

        //将用户信息存储到在线用户缓存中
        userLoginCache.getOnlineUserCache().put(loginToken,tdUser);
        userLoginCache.getOnlineAccountCache().put(userAccount,loginToken);

        return SystemResult.ok(loginToken);

    }


    /**
     *  功能：退出登录
     *  时间：2020/4/30 14:17
     *  参数：名称            类型            描述
     *       userAccount    String         用户账号
     *       loginToken     String         登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    public SystemResult logOut(String userAccount,String loginToken){
        String onlineUserToken = userLoginCache.getOnlineAccountCache().get(userAccount);
        //将当前用户推出在线容器中
        userLoginCache.getOnlineUserCache().remove(onlineUserToken);
        userLoginCache.getOnlineAccountCache().remove(userAccount);
        return SystemResult.ok();
    }
}

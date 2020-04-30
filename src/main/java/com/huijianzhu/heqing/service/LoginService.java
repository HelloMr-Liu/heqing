package com.huijianzhu.heqing.service;

import com.huijianzhu.heqing.vo.SystemResult;

/**
 * ================================================================
 * 说明：登录业务处理接口
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  14:14            创建
 * =================================================================
 **/
public interface LoginService {

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
    SystemResult login(String userAccount,String password);


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
    SystemResult logOut(String userAccount,String loginToken);

}

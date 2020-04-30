package com.huijianzhu.heqing.controller;

import com.huijianzhu.heqing.service.LoginService;
import com.huijianzhu.heqing.vo.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ================================================================
 * 说明：当前类说说明
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  16:04            创建
 * =================================================================
 **/
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

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
    @RequestMapping("/login")
    @ResponseBody
    public SystemResult login(String userAccount, String password){
        return loginService.login(userAccount, password);
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
    @RequestMapping("/logOut")
    @ResponseBody
    public SystemResult logOut(String userAccount,String loginToken){
        return loginService.logOut(userAccount, loginToken);
    }

}

package com.huijianzhu.heqing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ================================================================
 * 说明：当前类说说明
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  16:06            创建
 * =================================================================
 **/

@Controller
public class ShowController {

    @RequestMapping("/showLogin")
    public String showLogin(){
        return "login";
    }

    @RequestMapping("/showUserlist")
    public String showUserlist(){
        return "userList";
    }
}

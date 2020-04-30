package com.huijianzhu.heqing.controller;

import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.service.UserService;
import com.huijianzhu.heqing.vo.SystemResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ================================================================
 * 说明：用户信息操作控制器
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  14:38            创建
 * =================================================================
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    //注入用户业务操作接口
    @Autowired
    private UserService userService;

    /**
     *  功能：分页获取当前用户信息
     *  时间：2020/4/30 10:37
     *  参数：名称            类型            描述
     *       startPage      Integer        显示起始页
     *       showRow        Integer        每页显示的行数
     *       loginToken     String         用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/selectUserByPage")
    @ResponseBody
    public SystemResult selectUserByPage(Integer startPage,Integer showRow,String loginToken){
        return userService.selectUserByPage(startPage, showRow, loginToken);
    }


    /**
     *  功能：添加用户信息
     *  时间：2020/4/30 10:39
     *  参数：名称            类型            描述
     *       tdUser         TdUser         用户信息实体
     *       loginToken     String         用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public SystemResult addUser(TdUser tdUser, String loginToken){
        return userService.addUser(tdUser, loginToken);
    }


    /**
     *  功能：修改用户信息
     *  时间：2020/4/30 10:40
     *  参数：名称            类型            描述
     *       tdUser         TdUser         用户信息实体
     *       loginToken     String         用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public SystemResult updateUser(TdUser tdUser,String loginToken){
        return userService.updateUser(tdUser, loginToken);
    }


    /**
     *  功能：删除用户信息
     *  时间：2020/4/30 10:42
     *  参数：名称                       类型            描述
     *       deleteAccount         String            要删除的账号
     *       loginToken            String            用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public SystemResult deleteUser(String deleteAccount, String loginToken){
        return userService.deleteUser(deleteAccount, loginToken);
    }



    /**
     *  功能：获取用户权限信息
     *  时间：2020/4/30 11:31
     *  参数：名称                       类型            描述
     *       account                String            当前用户的账号
     *       loginToken             String            用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/getUserPermissions")
    @ResponseBody
    public SystemResult getUserPermissions(String account,String loginToken){
        return userService.getUserPermissions(account,loginToken);
    }


    /**
     *  功能：修改用户权限信息
     *  时间：2020/4/30 13:38
     *  参数：名称            类型            描述
     *       account        String         账号信息
     *       modelId        String         模块id
     *       loginToken     String         用户登录标志
     *
     *  返回：SystemResult
     *  描述：业务相应信息实体
     */
    @RequestMapping("/updateUserPermissions")
    @ResponseBody
    public SystemResult updateUserPermissions(String account,String modelId,String loginToken){
        return userService.updateUserPermissions(account, modelId, loginToken);
    }




}

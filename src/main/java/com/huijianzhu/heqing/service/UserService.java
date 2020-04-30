package com.huijianzhu.heqing.service;

import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.vo.SystemResult;

/**
 * ================================================================
 * 说明：用户信息业务接口
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:36            创建
 * =================================================================
 **/
public interface UserService {


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
    SystemResult selectUserByPage(Integer startPage,Integer showRow,String loginToken);



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
    SystemResult addUser(TdUser tdUser,String loginToken);



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
    SystemResult updateUser(TdUser tdUser,String loginToken);


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
    SystemResult deleteUser(String deleteAccount,String loginToken);



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
    SystemResult getUserPermissions(String account,String loginToken);


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
    SystemResult updateUserPermissions(String account,String modelId,String loginToken);


}
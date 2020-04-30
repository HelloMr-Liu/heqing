package com.huijianzhu.heqing.service.impl;

import com.huijianzhu.heqing.cache.PermissionsCache;
import com.huijianzhu.heqing.cache.UserCache;
import com.huijianzhu.heqing.cache.UserLoginCache;
import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.mapper.TdUserMapper;
import com.huijianzhu.heqing.pojo.MyUserPermission;
import com.huijianzhu.heqing.pojo.MyListPage;
import com.huijianzhu.heqing.service.UserService;
import com.huijianzhu.heqing.utils.MyUtils;
import com.huijianzhu.heqing.pojo.MyTree;
import com.huijianzhu.heqing.vo.SystemResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ================================================================
 * 说明：用户信息业务接口实现
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:42            创建
 * =================================================================
 **/
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserCache userCache;                //注入用户信息缓存

    @Autowired
    private UserLoginCache userLoginCache;      //注入用户登录信息缓存

    @Autowired
    private PermissionsCache permissionsCache;  //注入权限信息缓存

    @Autowired
    private TdUserMapper tdUserMapper;          //注入操作用户信息mapper接口

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
    public SystemResult selectUserByPage(Integer startPage,Integer showRow,String loginToken){
        List<TdUser> userList = userCache.getUserList();
        //将用户信息进行分页显示并返回给客户端
        MyListPage listPage = new MyListPage<TdUser>().getListPage(userList, startPage, showRow);
        return  SystemResult.ok(listPage);
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
    @Transactional(rollbackFor = Exception.class)
    public SystemResult addUser(TdUser tdUser, String loginToken){
        //获取当前在线用户信息
        TdUser tdUser1 = userLoginCache.getOnlineUserCache().get(loginToken);

        //二次对tdUser信息封装
        tdUser.setUserId(MyUtils.get32LengthUUID());
        tdUser.setUserAccount(userCache.getUserAccount());
        tdUser.setPassWord(MyUtils.getEncryption(tdUser.getPassWord()));
        tdUser.setPermissionsPath(permissionsCache.getPathByModelId(tdUser.getPermissionsId()));
        tdUser.setCreateTime(new Date());
        tdUser.setUpdateTime(new Date());
        tdUser.setDelFlag(1);
        tdUser.setCreateUserName(tdUser1.getUserName());

        //将数据持久化到数据库中
        tdUserMapper.insert(tdUser);

        //刷新下容器
        userCache.refreshCache();
        return SystemResult.ok();
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
    @Transactional(rollbackFor = Exception.class)
    public SystemResult updateUser(TdUser tdUser,String loginToken){
        //获取当前在线用户信息
        TdUser tdUser1 = userLoginCache.getOnlineUserCache().get(loginToken);

        //更新下当前权限信息
        tdUser.setPermissionsPath(permissionsCache.getPathByModelId(tdUser.getPermissionsId()));
        tdUser.setUpdateTime(new Date());
        tdUser.setCreateUserName(tdUser1.getCreateUserName());

        //将数据持久化到数据库中
        tdUserMapper.updateByPrimaryKey(tdUser);

        //刷新下容器
        userCache.refreshCache();
        return SystemResult.ok();
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
    @Transactional(rollbackFor = Exception.class)
    public SystemResult deleteUser(String deleteAccount,String loginToken){
        //获取当前在线用户信息
        TdUser tdUser1 = userLoginCache.getOnlineUserCache().get(loginToken);


        //获取要删除的用户信息
        TdUser tdUser = userCache.getUserCache().get(deleteAccount);
        tdUser.setUpdateTime(new Date());
        tdUser.setCreateUserName(tdUser1.getUserName());
        tdUser.setDelFlag(0); //逻辑删除

        //将数据持久化到数据库中
        tdUserMapper.updateByPrimaryKey(tdUser);

        //刷新下容器
        userCache.refreshCache();

        //判断当前删除的账号是否是本身自己
        if(deleteAccount.equals(tdUser1.getUserAccount())){
            //删除在线容器中对应的缓存信息
            userLoginCache.getOnlineUserCache().remove(loginToken);
            userLoginCache.getOnlineAccountCache().remove(tdUser1.getUserAccount());
        }
        return SystemResult.ok();
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
    public SystemResult getUserPermissions(String account,String loginToken){

        //获取当前用户信息
        TdUser tdUser = userCache.getUserCache().get(account);

        //创建一个MyUserPermission实体封装用户对应的拥有的id及所有的权限信息
        MyUserPermission userPermission=new MyUserPermission();
        userPermission.setAccount(account);
        userPermission.setModelList(tdUser.getPermissionsId());
        userPermission.setPermissionList(MyTree.getPermissions(permissionsCache.getPermissionsCache()));

        return SystemResult.ok(userPermission);
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
    @Transactional(rollbackFor = Exception.class)
    public SystemResult updateUserPermissions(String account,String modelId,String loginToken){

        //获取当前在线用户信息
        TdUser tdUser1 = userLoginCache.getOnlineUserCache().get(loginToken);

        //获取当前用户信息
        TdUser tdUser = userCache.getUserCache().get(account);

        tdUser.setUpdateTime(new Date());
        tdUser.setCreateUserName(tdUser1.getUserName());
        tdUser.setPermissionsId(modelId);
        tdUser.setPermissionsPath(permissionsCache.getPathByModelId(modelId));

        //将当前用户信息更新到数据中
        tdUserMapper.updateByPrimaryKey(tdUser);

        //刷新下容器
        userCache.refreshCache();

        //判断当前修改的用户权限是否处于在线状态
        String userToken = userLoginCache.getOnlineAccountCache().get(tdUser.getUserAccount());
        if(StringUtils.isNotBlank(userToken)){
            //由于用户处于在线状态进行修改在线用户权限
            TdUser tdUser2 = userCache.getUserCache().get(tdUser.getUserAccount());
            userLoginCache.getOnlineUserCache().put(userToken,tdUser2);
        }
        return SystemResult.ok();
    }






}

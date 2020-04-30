package com.huijianzhu.heqing.cache;

import com.huijianzhu.heqing.entity.TdPermissions;
import com.huijianzhu.heqing.mapper.TdPermissionsMapper;
import com.sun.org.apache.bcel.internal.generic.IFNULL;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ================================================================
 * 说明：权限信息缓存
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  11:00            创建
 * =================================================================
 **/

@Component
@Data
public class PermissionsCache {

    //注入操作权限信息mapper
    @Autowired
    private TdPermissionsMapper tdPermissionsMapper;

    //创建一个权限缓存容器
    private ConcurrentHashMap<String, TdPermissions> permissionsCache=new ConcurrentHashMap<>();

    /**
     *  功能：初始化容器
     *  时间：2020/4/30 11:09
     */
    @PostConstruct
    public void init(){
        refreshCache();
    }

    /**
     *  功能：刷新当前容器缓存
     *  时间：2020/4/30 11:07
     */
    public void refreshCache(){
        List<TdPermissions> tdPermissions = tdPermissionsMapper.selectPermissionsAll();
        tdPermissions.forEach(
            e->{permissionsCache.put(e.getModelId(),e);}
        );
    }

    /**
     *  功能：获取当前模块id对应的请求路径
     *  时间：2020/4/30 11:10
     *  参数：名称            类型            描述
     *       modelId        String         模块id
     *  返回：String
     *  描述：返回模块id对应的权限 如果有多个就以逗号隔开
     */
    public String getPathByModelId(String modelId){
        StringBuilder builder=new StringBuilder();
        //将多个模块id分割
        String[] modelIdArray = modelId.trim().split(",");
        for(int index=0;index<modelIdArray.length;index++){
            TdPermissions tdPermissions = permissionsCache.get(modelIdArray[index]);
            if(index==modelIdArray.length-1){
                if(StringUtils.isNotBlank(tdPermissions.getModelPath())){
                    builder.append(tdPermissions.getModelPath());
                }
            }else{
                if(StringUtils.isNotBlank(tdPermissions.getModelPath())){
                    builder.append(tdPermissions.getModelPath()).append(",");
                }
            }
        }
        return builder.toString();
    }
}

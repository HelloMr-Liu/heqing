package com.huijianzhu.heqing.pojo;

import com.huijianzhu.heqing.entity.TdPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ================================================================
 * 说明: 树结构信息
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  11:33            创建
 * =================================================================
 **/

public class MyTree {

    public MyTree(){}
    public MyTree(TdPermissions tdPermissions){
        this.parentModel=tdPermissions;
    }

    //创建一个父模块
    private  TdPermissions parentModel;

    //创建一个父模块下的子模块集
    private List<TdPermissions> childPrent=new ArrayList<>();


    //获取将一串数据转换成数结构
    public static  List<MyTree> getPermissions(ConcurrentHashMap<String,TdPermissions> permisMap){
        //将所有的父模块拿出来存储到一个treeMap中
        TreeMap<String,MyTree> parentTreeMap=new TreeMap<>();
        permisMap.entrySet().stream().forEach(
            e->{
                TdPermissions value = e.getValue();
                //判断是否是父模块还是子模块
                if(value.getIsParent()==0){
                    TdPermissions per=permisMap.get(value.getParentId());
                    //获取夫模块信息
                    if(per.getParentId().equals("001000")){
                        MyTree myTree =parentTreeMap.get(value.getParentId());
                        //判断parentTreeMap是否有对应父模块信息如果没有就添加
                        if(myTree==null){
                            myTree = new MyTree(permisMap.get(value.getParentId()));
                            //由于为先获取父模块信息存储到parentTreeMap中
                            parentTreeMap.put(value.getParentId(),myTree);
                        }
                        //将当前模块信息存储到父模块对应的子模块中
                        myTree.childPrent.add(value);
                    }
                }else {
                    //判断夫模块的parentID是否等于001000
                    if(value.getParentId().equals("001000")){
                        //当前是夫模块将当前模块判断当前夫模块在parentTreeMap中存在如果没有就创建
                        if(parentTreeMap.get(value.getModelId())==null){
                            //父模块信息存储到parentTreeMap中
                            parentTreeMap.put(value.getModelId(),new MyTree(value));
                        }
                    }

                }
            }
        );
        return parentTreeMap.entrySet().stream().map(e->{return e.getValue();}).collect(Collectors.toList());
    }




}

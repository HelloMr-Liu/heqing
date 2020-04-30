package com.huijianzhu.heqing.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================================
 * 说明：配置集合分页信息
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/29  14:21            创建
 * =================================================================
 **/
public class MyListPage<T> {

    public  Integer totalPage;       //总页数
    public  Integer totalRecord;     //总记录
    public  Integer currentPage;     //当前页数
    public  Integer currentShowRecord;//当前显示的记录数
    public  List<T> pageList;   //分页信息集合

    /**
     *  功能：获取对集合分页的信息
     *  时间：2020/4/29 14:30
     *  参数：名称            类型            描述
     *       objList        List<Object>   需要分页的集合
     *       startPage      Integer        起始页数
     *       showRecord     Integer        每页显示的条数
     *
     *  返回：MyListPage
     *  描述：封装了分页信息实体
     */
    public MyListPage getListPage(List<T> objList, Integer startPage, Integer showRecord){
        MyListPage myListPage=new MyListPage();

        myListPage.totalPage=(int)Math.ceil((double)objList.size()/showRecord);
        myListPage.totalRecord=objList.size();
        myListPage.currentPage=startPage;
        myListPage.currentShowRecord=showRecord;

        //一页
        if(objList.size()<=showRecord){
            myListPage.totalPage=1;
            myListPage.currentPage=1;
            myListPage.currentShowRecord=objList.size();
            myListPage.pageList=objList;
            return myListPage;
        }
        //如果显示页大于最大页就是最后页
        else if(myListPage.totalPage<startPage){
            myListPage.currentPage= myListPage.totalPage;
            List<Object> currList=new ArrayList<>();
            for(int index=objList.size()-showRecord;index<objList.size();index++){
                currList.add(objList.get(index));
            }
            myListPage.pageList=currList;
        }else{
            List<Object> currList=new ArrayList<>();
            int currShow =(startPage*showRecord)>objList.size()?objList.size():(startPage*showRecord)<1?showRecord>objList.size()?objList.size():showRecord:(startPage*showRecord);
            for(int index=(startPage!=0?startPage-1:0)*showRecord;index<currShow;index++){
                currList.add(objList.get(index));
            }
            myListPage.pageList=currList;
        }
        return myListPage;
    }
}

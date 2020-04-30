package com.huijianzhu.heqing.Interceptor;

import cn.hutool.json.JSONUtil;
import com.huijianzhu.heqing.cache.UserLoginCache;
import com.huijianzhu.heqing.entity.TdUser;
import com.huijianzhu.heqing.vo.SystemResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ================================================================
 * 说明：用户登录拦截器
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  14:45            创建
 * =================================================================
 **/

//@Component
public class LoginInterceptor  extends HandlerInterceptorAdapter {

    //获取当前在线用户缓存信息
    @Autowired
    private UserLoginCache userLoginCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求路径
        String url = request.getRequestURI(); // 这个方法只能获得不包含参数的请求url，且只包含相对路径

        //获取登录标志
        String userToken = request.getParameter("userToken");

        //判断是否处于在线用户容器中
        TdUser tdUser = userLoginCache.getOnlineUserCache().get(userToken);
        if(tdUser==null){
            //表明暂无权限访问
            response.getWriter().write(JSONUtil.toJsonStr(SystemResult.build(302,"请登录。。。")));
            //拦截
            return false;
        }

        //判断当前是否是管理员权限
        if(tdUser.getUserType()==1){
            //判断普通工用户是否拥有对应的请求信息
            String permissionsPath = tdUser.getPermissionsPath();
            if(StringUtils.isBlank(permissionsPath)){
                //表明暂无权限访问
                response.getWriter().write(JSONUtil.toJsonStr(SystemResult.build(302,"请登录。。。")));
            }

            //截取最后一个/后面的参数信息
            String lastOne=url.substring(url.lastIndexOf("/"));
            //截取倒数第二个/后面的参数信息
            String lastTwo=url.substring(0,url.lastIndexOf("/")).substring(url.lastIndexOf("/"));

            String currRequestPath=lastTwo+lastOne;
            //获取当前用户对应的请求权限信息并且转换成集合
            String[] split = permissionsPath.trim().split(",");
            List<String> collect = Arrays.stream(split).collect(Collectors.toList());
            for(String strUrl:collect){
                if(strUrl.indexOf(currRequestPath)!=-1){
                    //包含所以可以进行访问方向
                    return true;
                }
            }
            return false;
        }else{
            //非普通用户就是管理员所以直接放行
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在ModelAndView返回之后，异常处理
    }

    public static void main(String[] args) {
        String str="/liuzijiang/liu";
        System.out.println(str.indexOf("/liuzijiang/liu123"));

    }
}

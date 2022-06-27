package com.atguigu.interceptor;

import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.util.WebUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端登录拦截器
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class LoginInterceptor implements HandlerInterceptor {
   
   
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
         throws Exception {
      
   }

   public void postHandle(HttpServletRequest request, HttpServletResponse response,
         Object object, ModelAndView model) throws Exception {
      
   }

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object) throws Exception {
      UserInfo userInfo =(UserInfo) request.getSession().getAttribute("USER");
      if(null == userInfo) {
         Result result = Result.build("未登录", ResultCodeEnum.LOGIN_AUTH);
         WebUtil.writeJSON(response, result);//将数据转换为json返回客户端浏览器
         return false;//拒绝访问，返回错误提示
      }
      return true;//放行
   }
   
}
package cn.onearth.fmzs.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * Created by wliu on 2017/12/8 0008.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String name = (String)request.getSession().getAttribute("name");
        //处理ajax请求
        /*if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            if (null == name){
                request.getRequestDispatcher("/user/login/ajaxhandle").forward(request, response);
                return false;
            }
            return true;
        }*/

        if (StringUtils.containsAny(requestURI, "login", "index")){
            return true;
        }
        if (null == name){
            request.getRequestDispatcher("/login.html").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

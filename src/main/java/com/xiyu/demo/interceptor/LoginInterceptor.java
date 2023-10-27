package com.xiyu.demo.interceptor;

import com.xiyu.demo.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录拦截器
 * @author 陈子豪
 */
/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-09-26
 * Time: 18:24
 *
 * @author 陈子豪
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println(request.getRequestURL());
        if (user == null) {
            // 无论是在浏览器中输入URL还是通过AJAX触发后端接口，后端都可以获取URL信息并进行重定向。
            // 然而，当前端通过AJAX方式访问后端接口时，通常不会触发后端的重定向，因为后端无法获取浏览器的URL信息。
            if (isAjaxRequest(request)) {
                // 如果是AJAX请求，返回JSON响应，指示需要重定向到登录页面
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"redirect\":\"/views/login.html\"}");
            } else {
                // 浏览器中输入，执行页面重定向
                response.sendRedirect("/views/login.html");
            }
            return false;
        }
        return true;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}

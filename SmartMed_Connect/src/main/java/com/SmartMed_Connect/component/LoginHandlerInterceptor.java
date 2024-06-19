package com.SmartMed_Connect.component;

import com.SmartMed_Connect.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * 登录拦截器，用于检查用户是否已登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 在目标方法执行之前执行
         *
         * @param request  当前的 HTTP 请求对象
         * @param response 当前的 HTTP 响应对象
         * @param handler  处理器对象（可以是控制器方法、拦截器链等）
         * @return true 表示继续处理请求，false 表示中断请求
         * @throws Exception 可能抛出的异常
         */
        // 从当前会话中获取名为 "loginUser" 的属性，其应为已登录的 User 对象
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            // 如果未找到登录用户，即用户未登录
            // 将 HTTP 响应重定向到根路径（假设为登录页面）
            response.sendRedirect("/");
            // 返回 false 以中断请求，不再调用后续的拦截器或目标处理器
            return false;
        } else {
            // 如果找到了登录用户，即用户已登录
            // 返回 true 以继续处理请求，调用后续的拦截器或目标处理器
            return true;
        }
    }
}

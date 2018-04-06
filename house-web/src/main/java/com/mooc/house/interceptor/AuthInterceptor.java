package com.mooc.house.interceptor;

import com.google.common.base.Joiner;
import com.mooc.house.common.constant.CommonConstants;
import com.mooc.house.common.model.User;
//import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> {
            if (k.equals("errorMsg") || k.equals("successMsg") || k.equals("target")) {
                request.setAttribute(k, Joiner.on(",").join(v));
            }
        });
        String uri = request.getRequestURI();
        System.out.println("拦截uri:" + uri);
        if (uri.startsWith("/static") || uri.startsWith("/error")) {
            return true;
        }
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if (user != null) {
            UserContext.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}

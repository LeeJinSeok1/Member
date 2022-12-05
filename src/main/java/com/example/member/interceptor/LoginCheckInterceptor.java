package com.example.member.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인 여부 확인
// 로그인 하지 않은 상태라면 로그인 페이지로 보내고 로그인을 수행하면 직전에
// 요청한 주소로 보내줌.
// 로그인 상태라면 바로 넘어감
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        //요청한 주소 값을 가져오는 역할
        String requestURL = request.getRequestURI();
        System.out.println(requestURL);
        // 쎄션값을 가져오는 역할
        HttpSession session = request.getSession();
        // 로그인이 되어있는지 체크 해주고
        // 만약 로그인이 안되어있다면
        if(session.getAttribute("loginEmail") == null){
            // 이 주소로 보내준다
            response.sendRedirect("/memberPages/memberLogin?redirectURL"+ requestURL);
            return false;
        }
        return true;
    }
}

package org.example.expert.domain.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
public class AdminApiLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(AdminApiLoggingAspect.class);

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) || execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Long userId = (Long) request.getAttribute("userId");
        LocalDateTime requestTime = LocalDateTime.now();
        String url = request.getRequestURI();

        ObjectMapper objectMapper = new ObjectMapper();
        Object[] args = joinPoint.getArgs();
        String requestBody = objectMapper.writeValueAsString(args);

        log.info("Admin API 요청 - 사용자 ID: {}, 요청 시각: {}, URL: {}, 요청 본문: {}", userId, requestTime, url, requestBody);

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        String responseBody = objectMapper.writeValueAsString(result);

        log.info("Admin API 응답 - URL: {}, 응답 본문: {}", url, responseBody);

       return result;
    }
}

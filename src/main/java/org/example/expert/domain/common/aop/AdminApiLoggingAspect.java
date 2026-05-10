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

/**
 * Admin API 요청/응답 로깅 AOP
 * CommentAdminController와 UserAdmincontroller의 메서드 실행 전후에
 * 요청한 사용자 ID, 요청 시각, URL, 요청/응답 본문을 로깅한다.
 */

@Aspect
@Component
public class AdminApiLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(AdminApiLoggingAspect.class);

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) || execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {

        // 현재 HTTP 요청 객체 가져오기
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Long userId = (Long) request.getAttribute("userId");
        LocalDateTime requestTime = LocalDateTime.now();
        String url = request.getRequestURI();

        // 메서드 파라미터를 JSON으로 변환해서 요청 본문으로 로깅
        ObjectMapper objectMapper = new ObjectMapper();
        Object[] args = joinPoint.getArgs();
        String requestBody = objectMapper.writeValueAsString(args);

        log.info("Admin API 요청 - 사용자 ID: {}, 요청 시각: {}, URL: {}, 요청 본문: {}", userId, requestTime, url, requestBody);

        Object result = joinPoint.proceed(); // 실제 메서드 실행

        // 응답 결과를 JSON으로 변환해서 응답 본문으로 로깅
        String responseBody = objectMapper.writeValueAsString(result);

        log.info("Admin API 응답 - URL: {}, 응답 본문: {}", url, responseBody);

       return result;
    }
}

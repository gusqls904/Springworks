package com.study.work.common.aspect;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * HTTP 요청/응답 및 데이터베이스 쿼리 로깅을 위한 통합 AOP
 * 
 * @author Springworks
 * @version 1.0
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    @Value("${sensitive.fields}")
    private List<String> sensitiveFields;

    // ========================================
    // AOP 메서드들
    // ========================================
    
    /**
     * HTTP 요청/응답 로깅 AOP
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logHttpRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        logHttpRequest(joinPoint);
        
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logHttpResponse(result, duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logHttpError(e, duration);
            throw e;
        }
    }
    
    /**
     * MyBatis Mapper 메서드 실행 로깅
     */
    @Around("execution(* com.study.work..dao.*.*(..))")
    public Object logDatabaseQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        logDatabaseStart();
        
        try {
            Object result = joinPoint.proceed();
            logDatabaseEnd();
            return result;
        } catch (Exception e) {
            logDatabaseEnd();
            throw e;
        }
    }

    // ========================================
    // HTTP 로깅 메서드
    // ========================================
    
    private void logHttpRequest(ProceedingJoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;
            
            HttpServletRequest request = attributes.getRequest();
            String clientIp = getClientIpAddress(request);
            String method = request.getMethod();
            String url = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null) {
                url += "?" + queryString;
            }

            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, String> parameters = new HashMap<>();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                if (values.length == 1) {
                    if (isSensitiveParameter(key)) {
                        parameters.put(key, "****");
                    } else {
                        parameters.put(key, values[0]);
                    }
                } else {
                    parameters.put(key, String.join(",", values));
                }
            }

            String requestBody = extractRequestBody(joinPoint);

            log.info("───────────────────────────── [ REST API ] ─────────────────────────────");
            log.info("[HTTP REQUEST]");
            log.info("[TIME] {}", LocalDateTime.now().format(formatter));
            log.info("[CLIENT] {}", clientIp);
            log.info("[METHOD] {}", method);
            log.info("[URL] {}", url);
            
            if (!parameters.isEmpty()) {
                log.info("[PARAMS] {}", parameters);
            }
            
            if (!requestBody.isEmpty()) {
                log.info("[BODY] {}", requestBody);
            }
            
            log.info(" ");
            
        } catch (Exception e) {
            log.error("HTTP request logging error", e);
        }
    }

    private void logHttpResponse(Object result, long duration) {
        try {
            log.info("[HTTP RESPONSE]");
            log.info("[TIME] {}", LocalDateTime.now().format(formatter));
            log.info("[DURATION] {} ms", duration);
            
            if (result != null) {
                if (result instanceof org.springframework.http.ResponseEntity) {
                    org.springframework.http.ResponseEntity<?> responseEntity = (org.springframework.http.ResponseEntity<?>) result;
                    log.info("[STATUS] {}", responseEntity.getStatusCode().value());
                    
                    if (responseEntity.getBody() != null) {
                        String responseBody = objectMapper.writeValueAsString(responseEntity.getBody());
                        log.info("[DATA] {}", responseBody);
                    }
                } else {
                    log.info("[STATUS] 200");
                    String responseBody = objectMapper.writeValueAsString(result);
                    log.info("[DATA] {}", responseBody);
                }
            }
            log.info("──────────────────────────────────────────────────────────────────────────");

        } catch (Exception e) {
            log.error("HTTP response logging error", e);
        }
    }

    private void logHttpError(Exception e, long duration) {
        try {
            log.info("[HTTP ERROR]");
            log.info("[TIME] {}", LocalDateTime.now().format(formatter));
            log.info("[DURATION] {} ms", duration);
            
            // 에러 정보를 JSON 형태로 출력
            String errorJson = createErrorJson(e);
            log.info("[ERROR] {}", errorJson);
            
            log.info("──────────────────────────────────────────────────────────────────────────");
        } catch (Exception ex) {
            log.error("HTTP error logging error", ex);
        }
    }
    
    private String createErrorJson(Exception e) {
        try {
            Map<String, Object> errorInfo = new HashMap<>();
            
            if (e instanceof com.study.work.common.exception.BizException) {
                com.study.work.common.exception.BizException bizEx = (com.study.work.common.exception.BizException) e;
                errorInfo.put("type", "BizException");
                errorInfo.put("code", bizEx.getCode());
                errorInfo.put("message", bizEx.getMessage());
                errorInfo.put("status", bizEx.getStatus());
            } else {
                errorInfo.put("type", e.getClass().getSimpleName());
                errorInfo.put("message", e.getMessage());
                
                // 스택 트레이스 정보 추가
                StackTraceElement[] stackTrace = e.getStackTrace();
                if (stackTrace.length > 0) {
                    Map<String, Object> location = new HashMap<>();
                    location.put("class", stackTrace[0].getClassName());
                    location.put("method", stackTrace[0].getMethodName());
                    location.put("line", stackTrace[0].getLineNumber());
                    errorInfo.put("location", location);
                }
            }
            
            return objectMapper.writeValueAsString(errorInfo);
        } catch (Exception ex) {
            return "{\"type\":\"Error\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }

    // ========================================
    // DB 로깅 메서드
    // ========================================
    
    private void logDatabaseStart() {
        log.info("[SQL]");
    }
    
    private void logDatabaseEnd() {
        log.info(" ");
    }

    // ========================================
    // 유틸리티 메서드
    // ========================================
    
    private String extractRequestBody(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg != null && !isPrimitiveOrWrapper(arg.getClass())) {
                    String json = objectMapper.writeValueAsString(arg);
                    return maskSensitiveData(json);
                }
            }
        } catch (Exception e) {
            log.debug("Request body extraction error: {}", e.getMessage());
        }
        return "";
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() || 
               type == String.class || 
               type == Integer.class || 
               type == Long.class || 
               type == Double.class || 
               type == Float.class || 
               type == Boolean.class ||
               type == Character.class ||
               type == Byte.class ||
               type == Short.class;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    private boolean isSensitiveParameter(String parameterName) {
        if (sensitiveFields == null || sensitiveFields.isEmpty()) {
            return false;
        }
        
        String lowerName = parameterName.toLowerCase();
        return sensitiveFields.stream()
                .anyMatch(field -> lowerName.contains(field.toLowerCase()));
    }
    
    private String maskSensitiveData(String json) {
        try {
            // 디버깅을 위한 로그 추가
            log.debug("Original JSON: {}", json);
            
            if (sensitiveFields == null || sensitiveFields.isEmpty()) {
                return json;
            }
            
            String result = json;
            
            for (String field : sensitiveFields) {
                // 해당 필드의 값을 ****로 마스킹
                String pattern = "\"" + field + "\"\\s*:\\s*\"[^\"]*\"";
                String replacement = "\"" + field + "\":\"****\"";
                result = result.replaceAll(pattern, replacement);
                log.debug("Masked field '{}': {} -> {}", field, pattern, replacement);
            }
            
            log.debug("Masked JSON: {}", result);
            return result;
        } catch (Exception e) {
            log.debug("Sensitive data masking error: {}", e.getMessage());
            return json;
        }
    }
}

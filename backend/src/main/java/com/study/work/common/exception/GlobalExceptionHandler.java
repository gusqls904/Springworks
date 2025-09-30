package com.study.work.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.study.work.common.dto.CommonResultDTO;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String PRODUCTION_PROFILE = "prod";

    @ExceptionHandler(BizException.class)
    public ResponseEntity<CommonResultDTO> handleBizException(BizException ex, WebRequest request) {
        
        HttpStatus httpStatus = determineHttpStatus(ex.getCode());
        CommonResultDTO result = CommonResultDTO.error(ex.getCode(), ex.getMessage(), httpStatus.value());

        return ResponseEntity.status(httpStatus).body(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResultDTO> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String requestUri = request.getDescription(false).replace("uri=", "");
        log.warn("잘못된 인수 예외 발생 - URI: {}, Message: {}", requestUri, ex.getMessage());
        
        CommonResultDTO result = CommonResultDTO.error(
            "40000001", 
            "잘못된 요청입니다.", 
            HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResultDTO> handleException(Exception ex, WebRequest request) {
        String requestUri = request.getDescription(false).replace("uri=", "");
        log.error("예상치 못한 오류 발생 - URI: {}", requestUri, ex);
        
        CommonResultDTO result = CommonResultDTO.error(
            CommonResultDTO.DEFAULT_ERROR_CODE, 
            CommonResultDTO.DEFAULT_ERROR_MESSAGE, 
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    /**
     * 에러 코드에 따른 HTTP 상태 코드 결정
     */
    private HttpStatus determineHttpStatus(String code) {
        if (code == null || code.length() < 3) {
            return HttpStatus.BAD_REQUEST;
        }
        
        char firstChar = code.charAt(0);
        switch (firstChar) {
            case '4':
                return HttpStatus.BAD_REQUEST;
            case '5':
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * 운영 환경 여부 확인
     */
    private boolean isProductionEnvironment() {
        String activeProfile = System.getProperty("spring.profiles.active");
        return PRODUCTION_PROFILE.equals(activeProfile);
    }
}

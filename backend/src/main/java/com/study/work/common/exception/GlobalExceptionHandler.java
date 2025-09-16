package com.study.work.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.study.work.common.dto.CommonResultDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<CommonResultDTO> handleBizException(BizException ex) {
    	CommonResultDTO result = new CommonResultDTO();
        result.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        result.setCode(ex.getCode());
        result.setMessage(ex.getMessage());
        result.setStatus("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResultDTO> handleException(Exception ex) {
    	CommonResultDTO result = new CommonResultDTO();
        result.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setCode("50000000");
        result.setMessage("예상치 못한 오류가 발생했습니다.");
        result.setDetailMessage(ex.getMessage());
        result.setStatus("FAIL");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}

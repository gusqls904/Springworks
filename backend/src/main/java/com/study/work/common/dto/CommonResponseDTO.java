package com.study.work.common.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDTO<T> extends CommonResultDTO {

    private T body;

    public CommonResponseDTO() {
        super();
    }

    public CommonResponseDTO(T body) {
        super();
        this.body = body;
    }

    public CommonResponseDTO(T body, String code, String message) {
        super();
        this.body = body;
        this.setCode(code);
        this.setMessage(message);
    }

    public T getBody() { 
        return body; 
    }

    public void setBody(T body) {
        // null 체크 제거 - null도 유효한 응답으로 처리
        this.body = body;
    }

    // 정적 팩토리 메서드 추가
    public static <T> CommonResponseDTO<T> success(T body) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>(body);
        response.setStatus(SUCCESS_STATUS);
        return response;
    }

    public static <T> CommonResponseDTO<T> success(T body, String message) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>(body);
        response.setMessage(message);
        response.setStatus(SUCCESS_STATUS);
        return response;
    }

    public static <T> CommonResponseDTO<T> errorResponse(String code, String message) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>();
        response.setCode(code);
        response.setMessage(message);
        response.setStatus(FAIL_STATUS);
        response.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        return response;
    }

    public static <T> CommonResponseDTO<T> errorResponse(String code, String message, int httpStatusCode) {
        CommonResponseDTO<T> response = new CommonResponseDTO<>();
        response.setCode(code);
        response.setMessage(message);
        response.setStatus(FAIL_STATUS);
        response.setHttpStatusCode(httpStatusCode);
        return response;
    }

    // 편의 메서드
    public boolean hasBody() {
        return body != null;
    }

    public boolean isEmpty() {
        return body == null;
    }
}
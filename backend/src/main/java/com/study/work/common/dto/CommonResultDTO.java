package com.study.work.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 공통 응답 전문 기본 구조 (상위)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResultDTO {

    // 상수 정의
    public static final String DEFAULT_SUCCESS_CODE = "20000000";
    public static final String DEFAULT_SUCCESS_MESSAGE = "정상 처리 되었습니다.";
    public static final String DEFAULT_ERROR_CODE = "50000000";
    public static final String DEFAULT_ERROR_MESSAGE = "예상치 못한 오류가 발생했습니다.";
    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String FAIL_STATUS = "FAIL";

    private int httpStatusCode = HttpStatus.OK.value();
    private String code = DEFAULT_SUCCESS_CODE;
    private String message = DEFAULT_SUCCESS_MESSAGE;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;                                // SUCCESS / FAIL 등 상태

    @JsonIgnore
    private HttpHeaders httpHeaders;                      // 응답 헤더 (JSON 직렬화 제외)

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = (code != null && !code.trim().isEmpty()) ? code : DEFAULT_SUCCESS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = (message != null && !message.trim().isEmpty()) ? message : DEFAULT_SUCCESS_MESSAGE;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 편의 메서드 추가
    public boolean isSuccess() {
        return SUCCESS_STATUS.equals(this.status);
    }

    public boolean isFail() {
        return FAIL_STATUS.equals(this.status);
    }

    // 정적 팩토리 메서드 추가
    public static CommonResultDTO success() {
        CommonResultDTO result = new CommonResultDTO();
        result.setStatus(SUCCESS_STATUS);
        return result;
    }

    public static CommonResultDTO success(String message) {
        CommonResultDTO result = new CommonResultDTO();
        result.setMessage(message);
        result.setStatus(SUCCESS_STATUS);
        return result;
    }

    public static CommonResultDTO error(String code, String message) {
        CommonResultDTO result = new CommonResultDTO();
        result.setCode(code);
        result.setMessage(message);
        result.setStatus(FAIL_STATUS);
        result.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    public static CommonResultDTO error(String code, String message, int httpStatusCode) {
        CommonResultDTO result = new CommonResultDTO();
        result.setCode(code);
        result.setMessage(message);
        result.setStatus(FAIL_STATUS);
        result.setHttpStatusCode(httpStatusCode);
        return result;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

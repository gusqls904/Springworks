package com.study.work.common.exception;

import com.study.work.common.dto.CommonResultDTO;

public class BizException extends RuntimeException {

    private final String code;
    private final String message;
    private final String status;

    public BizException(String key, ExceptionInfoConfig config) {
        super(config.getExceptionInfoResult(key).getMessage());
        CommonResultDTO result = config.getExceptionInfoResult(key);
        this.code = result.getCode();
        this.message = result.getMessage();
        this.status = result.getStatus();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = "FAIL";
    }

    public String getCode() { return code; }
    @Override
    public String getMessage() { return message; }
    public String getStatus() { return status; }
}

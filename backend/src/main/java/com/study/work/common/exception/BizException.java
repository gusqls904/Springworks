package com.study.work.common.exception;

import com.study.work.common.dto.CommonResultDTO;

public class BizException extends RuntimeException {

    private final String code;
    private final String message;
    private final String status;

    // YAML key 기반 생성자
    public BizException(String key) {
        this(ExceptionInfoConfig.getInstance().getExceptionInfoResult(key));
    }

    // YAML key + 동적 값 기반 생성자
    public BizException(String key, Object... args) {
        this(ExceptionInfoConfig.getInstance().getResultDto(key, args));
    }

    // CommonResultDTO 기반 생성자
    private BizException(CommonResultDTO result) {
        super(result.getMessage());
        this.code = result.getCode();
        this.message = result.getMessage();
        this.status = result.getStatus();
    }

    // 코드 + 메시지 직접 생성
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

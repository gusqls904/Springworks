package com.study.work.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.work.common.exception.BizException;
import com.study.work.common.exception.ExceptionInfoConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDTO<T> extends CommonResultDTO {

    private T body;

    public CommonResponseDTO() {}

    public CommonResponseDTO(T body) {
        this.setBody(body);
    }

    public T getBody() { return body; }

    public void setBody(T body) {
        if (body == null) {
            throw new BizException("data_not_found", new ExceptionInfoConfig("error-codes.yml"));
        }
        this.body = body;
    }
}


package com.study.work.common.exception;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.study.work.common.dto.CommonResultDTO;

@Component
@SuppressWarnings("unchecked")
public class ExceptionInfoConfig {

    private static ExceptionInfoConfig instance; // 정적 인스턴스

    private Map<String, Object> exceptionInfo;
    private CommonResultDTO successResultDto;
    private CommonResultDTO undefinedErrorResult;

    public ExceptionInfoConfig() {
        instance = this; // Bean 생성 시 static instance 세팅
    }

    public static ExceptionInfoConfig getInstance() {
        return instance;
    }

    public ExceptionInfoConfig(String filePath) {
        instance = this;

        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource(filePath));
        this.exceptionInfo = yaml.getObject();

        // Success 기본값
        Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
        Map<String, Object> successInfo = (Map<String, Object>) successInfos.get("200ok");
        successResultDto = new CommonResultDTO();
        successResultDto.setCode(successInfo.get("code") != null ? successInfo.get("code").toString() : "");
        successResultDto.setMessage(successInfo.get("desc") != null ? successInfo.get("desc").toString() : "");
        successResultDto.setStatus(successInfo.get("status") != null ? successInfo.get("status").toString() : "");

        // Undefined Error 기본값
        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get("notdefine");
        undefinedErrorResult = new CommonResultDTO();
        undefinedErrorResult.setCode(exceptionInfo1.get("code") != null ? exceptionInfo1.get("code").toString() : "");
        undefinedErrorResult.setMessage(exceptionInfo1.get("desc") != null ? exceptionInfo1.get("desc").toString() : "");
        undefinedErrorResult.setStatus(exceptionInfo1.get("status") != null ? exceptionInfo1.get("status").toString() : "");
    }

    public CommonResultDTO getExceptionInfoResult(String ymlKey) {
        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get(ymlKey);
        if (exceptionInfo1 == null) return undefinedErrorResult;

        CommonResultDTO result = new CommonResultDTO();
        result.setCode(exceptionInfo1.get("code") != null ? exceptionInfo1.get("code").toString() : "");
        result.setMessage(exceptionInfo1.get("desc") != null ? exceptionInfo1.get("desc").toString() : "");
        result.setStatus(exceptionInfo1.get("status") != null ? exceptionInfo1.get("status").toString() : "");
        return result;
    }

    public CommonResultDTO getSuccessInfoResult(String ymlKey) {
        Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
        Map<String, Object> successInfo = (Map<String, Object>) successInfos.get(ymlKey);
        if (successInfo == null) return successResultDto;

        CommonResultDTO result = new CommonResultDTO();
        result.setCode(successInfo.get("code") != null ? successInfo.get("code").toString() : "");
        result.setMessage(successInfo.get("desc") != null ? successInfo.get("desc").toString() : "");
        result.setStatus(successInfo.get("status") != null ? successInfo.get("status").toString() : "");
        return result;
    }

    public CommonResultDTO getUndefinedErrorResult() {
        return undefinedErrorResult;
    }

    public CommonResultDTO getResultDto(String ymlKey, Object... args) {
        CommonResultDTO result = getExceptionInfoResult(ymlKey);
        String desc = result.getMessage();
        if (args != null && args.length > 0) {
            result.setMessage(MessageFormat.format(desc, args));
        }
        return result;
    }
}
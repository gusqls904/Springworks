package com.study.work.common.exception;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.study.work.common.dto.CommonResultDTO;
import com.study.work.common.util.StringUtils;

@SuppressWarnings("unchecked")
public class ExceptionInfoConfig {

    private Map<String, Object> exceptionInfo;
    private CommonResultDTO successResultDto;
    private CommonResultDTO undefinedErrorResult;

    public ExceptionInfoConfig(String filePath) {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource(filePath));
        this.exceptionInfo = yaml.getObject();

        // Success 기본값
        Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
        Map<String, Object> successInfo = (Map<String, Object>) successInfos.get("200ok");
        successResultDto = new CommonResultDTO();
        successResultDto.setCode(StringUtils.objectIfNullToEmpty(successInfo.get("code")));
        successResultDto.setMessage(StringUtils.objectIfNullToEmpty(successInfo.get("desc")));
        successResultDto.setStatus(StringUtils.objectIfNullToEmpty(successInfo.get("status")));

        // Undefined Error 기본값
        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get("notdefine");
        undefinedErrorResult = new CommonResultDTO();
        undefinedErrorResult.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("code")));
        undefinedErrorResult.setMessage(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("desc")));
        undefinedErrorResult.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("status")));
    }

    public CommonResultDTO getExceptionInfoResult(String ymlKey) {
        Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
        Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get(ymlKey);
        if (exceptionInfo1 == null) return undefinedErrorResult;

        CommonResultDTO result = new CommonResultDTO();
        result.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("code")));
        result.setMessage(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("desc")));
        result.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo1.get("status")));
        return result;
    }

    public CommonResultDTO getSuccessInfoResult(String ymlKey) {
        Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
        Map<String, Object> successInfo = (Map<String, Object>) successInfos.get(ymlKey);
        if (successInfo == null) return successResultDto;

        CommonResultDTO result = new CommonResultDTO();
        result.setCode(StringUtils.objectIfNullToEmpty(successInfo.get("code")));
        result.setMessage(StringUtils.objectIfNullToEmpty(successInfo.get("desc")));
        result.setStatus(StringUtils.objectIfNullToEmpty(successInfo.get("status")));
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


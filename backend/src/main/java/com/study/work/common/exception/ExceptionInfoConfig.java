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
        initializeExceptionInfoWithPath("exception/exception.yml");
    }

    public static ExceptionInfoConfig getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ExceptionInfoConfig가 초기화되지 않았습니다.");
        }
        return instance;
    }

    public ExceptionInfoConfig(String filePath) {
        instance = this;
        initializeExceptionInfoWithPath(filePath);
    }

    private void initializeExceptionInfoWithPath(String filePath) {
        try {
            YamlMapFactoryBean yaml = new YamlMapFactoryBean();
            yaml.setResources(new ClassPathResource(filePath));
            this.exceptionInfo = yaml.getObject();

            if (this.exceptionInfo == null) {
                throw new RuntimeException("YAML 파일을 로드할 수 없습니다: " + filePath);
            }

            // Success 기본값
            Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
            if (successInfos != null) {
                Map<String, Object> successInfo = (Map<String, Object>) successInfos.get("200ok");
                if (successInfo != null) {
                    successResultDto = new CommonResultDTO();
                    successResultDto.setCode(getStringValue(successInfo, "code", CommonResultDTO.DEFAULT_SUCCESS_CODE));
                    successResultDto.setMessage(getStringValue(successInfo, "desc", CommonResultDTO.DEFAULT_SUCCESS_MESSAGE));
                    successResultDto.setStatus(getStringValue(successInfo, "status", CommonResultDTO.SUCCESS_STATUS));
                }
            }

            // Undefined Error 기본값
            Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
            if (exceptionInfos != null) {
                Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get("notdefine");
                if (exceptionInfo1 != null) {
                    undefinedErrorResult = new CommonResultDTO();
                    undefinedErrorResult.setCode(getStringValue(exceptionInfo1, "code", CommonResultDTO.DEFAULT_ERROR_CODE));
                    undefinedErrorResult.setMessage(getStringValue(exceptionInfo1, "desc", CommonResultDTO.DEFAULT_ERROR_MESSAGE));
                    undefinedErrorResult.setStatus(getStringValue(exceptionInfo1, "status", CommonResultDTO.FAIL_STATUS));
                }
            }

            // 기본값이 설정되지 않은 경우 기본값으로 초기화
            if (successResultDto == null) {
                successResultDto = CommonResultDTO.success();
            }
            if (undefinedErrorResult == null) {
                undefinedErrorResult = CommonResultDTO.error(CommonResultDTO.DEFAULT_ERROR_CODE, CommonResultDTO.DEFAULT_ERROR_MESSAGE);
            }

        } catch (Exception e) {
            // 초기화 실패 시 기본값으로 설정
            successResultDto = CommonResultDTO.success();
            undefinedErrorResult = CommonResultDTO.error(CommonResultDTO.DEFAULT_ERROR_CODE, CommonResultDTO.DEFAULT_ERROR_MESSAGE);
            this.exceptionInfo = new java.util.HashMap<>();
            throw new RuntimeException("ExceptionInfoConfig 초기화 실패, 기본값으로 설정됨: " + filePath, e);
        }
    }

    private String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    public CommonResultDTO getExceptionInfoResult(String ymlKey) {
        if (ymlKey == null || ymlKey.trim().isEmpty()) {
            return undefinedErrorResult;
        }

        try {
            Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionInfo.get("exception");
            if (exceptionInfos == null) {
                return undefinedErrorResult;
            }

            Map<String, Object> exceptionInfo1 = (Map<String, Object>) exceptionInfos.get(ymlKey);
            if (exceptionInfo1 == null) {
                return undefinedErrorResult;
            }

            CommonResultDTO result = new CommonResultDTO();
            result.setCode(getStringValue(exceptionInfo1, "code", CommonResultDTO.DEFAULT_ERROR_CODE));
            result.setMessage(getStringValue(exceptionInfo1, "desc", CommonResultDTO.DEFAULT_ERROR_MESSAGE));
            result.setStatus(getStringValue(exceptionInfo1, "status", CommonResultDTO.FAIL_STATUS));
            return result;
        } catch (Exception e) {
            return undefinedErrorResult;
        }
    }

    public CommonResultDTO getSuccessInfoResult(String ymlKey) {
        if (ymlKey == null || ymlKey.trim().isEmpty()) {
            return successResultDto;
        }

        try {
            Map<String, Object> successInfos = (Map<String, Object>) exceptionInfo.get("success");
            if (successInfos == null) {
                return successResultDto;
            }

            Map<String, Object> successInfo = (Map<String, Object>) successInfos.get(ymlKey);
            if (successInfo == null) {
                return successResultDto;
            }

            CommonResultDTO result = new CommonResultDTO();
            result.setCode(getStringValue(successInfo, "code", CommonResultDTO.DEFAULT_SUCCESS_CODE));
            result.setMessage(getStringValue(successInfo, "desc", CommonResultDTO.DEFAULT_SUCCESS_MESSAGE));
            result.setStatus(getStringValue(successInfo, "status", CommonResultDTO.SUCCESS_STATUS));
            return result;
        } catch (Exception e) {
            return successResultDto;
        }
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
package com.study.work.learning.user.ctl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.study.work.common.dto.CommonResponseDTO;
import com.study.work.learning.user.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.study.work.learning.user.dto.UserDTO;
import com.study.work.learning.user.svc.UserSVC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "회원 관리", description = "회원가입, 로그인 등 사용자 계정 관리 API")
public class UserCTL {

    @Autowired
    private UserSVC userSVC;
    
    @Operation(summary = "역할 목록 조회", description = "회원가입 팝업 오픈 시")
    @PostMapping("/getRoleList")
    public CommonResponseDTO<List<RoleDTO>> getRoles() {
    	
        List<RoleDTO> roleList = userSVC.getRoleList();
        
        return new CommonResponseDTO<List<RoleDTO>>(roleList);
    }
    
    @Operation(summary = "아이디 중복체크", description = "회원가입 팝업에서 중복체크 버튼 클릭 시")
    @PostMapping("/checkUserId")
    public CommonResponseDTO<Map<String, Object>> checkUserId(@RequestBody Map<String, String> reqMap) {
    	
    	String userId = reqMap.get("userId");
    	
        boolean isDuplicate = userSVC.checkUserId(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("duplicate", isDuplicate);
        
        return new CommonResponseDTO<>(result);
    }
    

    @Operation(summary = "회원가입", description = "회원가입 팝업에서 회원가입 버튼 클릭 시")
    @PostMapping("/signup")
    public CommonResponseDTO<Void> signup(@RequestBody UserDTO userDto) {
        userSVC.signup(userDto);
        
        return new CommonResponseDTO<Void>();
    }


}

package com.study.work.learning.user.ctl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    @Operation(summary = "아이디 중복체크", description = "사용자의 아이디의 중복체크를 진행합니다.")
    @PostMapping("/checkUserId")
    public ResponseEntity<Map<String, Object>> checkUserId(@RequestBody Map<String, String> reqMap) {
    	/**
    	 * TODO
    	 * 1. web에서 입력한 사용자 아이디 요청데이터 받기
    	 * 2. db 조회를 위해 svc로 이동
    	 * 3. svc에서 dao로 이동
    	 * 4. dao에 입력한 메서드값 mapper에서 쿼리 확인
    	 * 5. 조회된 쿼리 정보 확인
    	 * 		5-1 중복 시 isDuplicate -> true 
    	 * 		5-2 사용가능 시 isDuplicate -> false
    	 * 6. 응답 할 데이터 세팅
    	 * 7. web으로 응답데이터 보내기
    	 */
    	
    	// 1번
    	String userId = reqMap.get("userId");
    	
    	// 2번
        boolean isDuplicate = userSVC.checkUserId(userId);
        
        // 6번
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("duplicate", isDuplicate);

        if (isDuplicate) {
            result.put("message", "이미 사용 중인 아이디입니다.");
        } else {
            result.put("message", "사용 가능한 아이디입니다.");
        }
        
        // 7번
        return ResponseEntity.ok(result);
    }
    

    @Operation(summary = "회원가입", description = "사용자가 입력한 정보로 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserDTO userDto) {
        userSVC.signup(userDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/getRoleList")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        List<RoleDTO> roles = userSVC.getRoleList();
        return ResponseEntity.ok(roles);
    }

}

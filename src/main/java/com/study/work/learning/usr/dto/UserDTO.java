package com.study.work.learning.usr.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	/**
	 * TB_USER 테이블의 컬럼 카멜케이스로 입력
	 */
	
	/* 사용자 ID */
	String userId;
	
	/* 사용자명 */
	String userName;
	
	/* 비밀번호 (암호화 저장) */
	String password;
	
	/* 이메일 */
	String email;
	
	/* 사용자 역할 */
	String role;
	
	/* 가입일 */
	String createdDttm;
	
	/* 정보 수정일 */
	String updatedDttm;
	
	/* 활성 여부 (0: 비활성, 1: 활성) */
	String isActive;

}

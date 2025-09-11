package com.study.work.learning.usr.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.work.learning.usr.dto.UserDTO;
import com.study.work.learning.usr.svc.UserSVC;

@RestController
@RequestMapping(value = "/user")
public class UserCTL {

	@Autowired
	private UserSVC usrSVC;


	/**
	 * 회원가입
	 */
	@RequestMapping(value = "/joinMember", method = RequestMethod.POST)
	@ResponseBody
	public void joinMember(UserDTO userDto) {
		
		/** TODO
		 * 1. web에서 입력한 정보 UserDTO에 담아서 가져오기
		 * 2. UserDTO를 UserSVC의 joinMember 메서드로 전달
		 * 3. 예외처리
		 * 4. 응답 처리
		 *    - 성공 시: HTTP 201 Created + 회원가입 성공 메시지
		 *    - 실패 시: HTTP 4xx/5xx + 구체적인 에러 메시지
		 *    - JSON 형태로 응답 데이터 구성
		 */
		
		usrSVC.joinMember(userDto);

	}

}

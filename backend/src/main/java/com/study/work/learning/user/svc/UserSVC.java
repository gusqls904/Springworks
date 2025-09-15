package com.study.work.learning.user.svc;

import com.study.work.learning.user.dto.UserDTO;

public interface UserSVC {

	void signup(UserDTO userDto);

	// 2번
	boolean checkUserId(String userId);

}


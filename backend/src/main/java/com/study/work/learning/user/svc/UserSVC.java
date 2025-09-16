package com.study.work.learning.user.svc;

import com.study.work.learning.user.dto.RoleDTO;
import com.study.work.learning.user.dto.UserDTO;

import java.util.List;

public interface UserSVC {

	void signup(UserDTO userDto);

	// 2번
	boolean checkUserId(String userId);

    List<RoleDTO> getRoleList();
}


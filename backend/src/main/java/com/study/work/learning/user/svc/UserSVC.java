package com.study.work.learning.user.svc;

import com.study.work.learning.user.dto.RoleDTO;
import com.study.work.learning.user.dto.UserDTO;

import java.util.List;

public interface UserSVC {

	List<RoleDTO> getRoleList();
	
	boolean checkUserId(String userId);
	
	void signup(UserDTO userDto);
}


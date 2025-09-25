package com.study.work.admin.user.svc;

import com.study.work.admin.user.dto.RoleDTO;
import com.study.work.admin.user.dto.UserDTO;

import java.util.List;

public interface UserSVC {

	List<RoleDTO> getRoleList();
	
	boolean checkUserId(String userId);
	
	void signup(UserDTO userDto);
}


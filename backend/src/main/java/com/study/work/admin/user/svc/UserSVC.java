package com.study.work.admin.user.svc;

import com.study.work.admin.user.dto.RoleDTO;
import com.study.work.admin.user.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserSVC {

	List<RoleDTO> getRoleList();
	
	boolean checkUserId(String userId);
	
	void signup(UserDTO userDto);

    List<Map<String,Object>> getUserList(int page, int size, String searchType, String searchQuery);

    int getUserCount(String searchType, String searchQuery);
}


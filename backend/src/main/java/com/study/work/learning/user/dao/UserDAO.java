package com.study.work.learning.user.dao;


import com.study.work.learning.user.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

import com.study.work.learning.user.dto.UserDTO;

import java.util.List;

@Mapper
public interface UserDAO {

	void insertUser(UserDTO userDto);

	// 4번
	int selectCountUserId(String userId);

    List<RoleDTO> getRoleList();
}
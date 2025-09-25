package com.study.work.admin.user.dao;


import com.study.work.admin.user.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

import com.study.work.admin.user.dto.UserDTO;

import java.util.List;

@Mapper
public interface UserDAO {

	List<RoleDTO> getRoleList();

	int selectCountUserId(String userId);

	void insertUser(UserDTO userDto);
}
package com.study.work.admin.user.dao;


import com.study.work.admin.user.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

import com.study.work.admin.user.dto.UserDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {

	List<RoleDTO> getRoleList();

	int selectCountUserId(String userId);

	void insertUser(UserDTO userDto);

    List<Map<String,Object>> getUserList(int offset, int size, String searchType, String searchQuery);

    int getUserCount(String searchType, String searchQuery);
}
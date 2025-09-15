package com.study.work.learning.user.dao;


import org.apache.ibatis.annotations.Mapper;

import com.study.work.learning.user.dto.UserDTO;

@Mapper
public interface UserDAO {

	void insertUser(UserDTO userDto);

	// 4번
	int selectCountUserId(String userId);
    

}
package com.study.work.learning.user.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.work.learning.user.dao.UserDAO;
import com.study.work.learning.user.dto.UserDTO;
import com.study.work.learning.user.svc.UserSVC;

@Service
public class UserSVCImpl implements UserSVC{
	
	@Autowired
	UserDAO userDAO;

	@Override
	public void signup(UserDTO userDto) {
		// TODO Auto-generated method stub
		
		userDAO.insertUser(userDto);
		
		
	}

}

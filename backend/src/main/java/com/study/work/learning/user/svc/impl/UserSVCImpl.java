package com.study.work.learning.user.svc.impl;

import com.study.work.learning.user.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.work.learning.user.dao.UserDAO;
import com.study.work.learning.user.dto.UserDTO;
import com.study.work.learning.user.svc.UserSVC;

import java.util.List;

@Service
public class UserSVCImpl implements UserSVC{
	
	@Autowired
	UserDAO userDAO;

	@Override
	public void signup(UserDTO userDto) {
		// TODO Auto-generated method stub
		
		userDAO.insertUser(userDto);
	}

	@Override
	public boolean checkUserId(String userId) {
		// TODO Auto-generated method stub
		
		// 3번
		if(userDAO.selectCountUserId(userId) > 0) {
			// 5-1번
			return true;
		};
		// 5-2번
		return false;
	}

    @Override
    public List<RoleDTO> getRoleList() {
        return userDAO.getRoleList();
    }


}

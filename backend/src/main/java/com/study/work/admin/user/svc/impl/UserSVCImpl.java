package com.study.work.admin.user.svc.impl;

import com.study.work.admin.user.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.work.admin.user.dao.UserDAO;
import com.study.work.admin.user.dto.UserDTO;
import com.study.work.admin.user.svc.UserSVC;

import java.util.List;

@Service
public class UserSVCImpl implements UserSVC{
	
	@Autowired
	UserDAO userDAO;

	@Override
	public void signup(UserDTO userDto) {
		
		userDAO.insertUser(userDto);
	}

	@Override
	public boolean checkUserId(String userId) {
		
		if(userDAO.selectCountUserId(userId) > 0) {
			return true;
		};
		return false;
	}

    @Override
    public List<RoleDTO> getRoleList() {
    	
        return userDAO.getRoleList();
    }
}

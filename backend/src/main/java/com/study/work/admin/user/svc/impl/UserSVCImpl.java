package com.study.work.admin.user.svc.impl;

import com.study.work.admin.user.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.work.admin.user.dao.UserDAO;
import com.study.work.admin.user.dto.UserDTO;
import com.study.work.admin.user.svc.UserSVC;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<Map<String,Object>> getUserList(int page, int size, String searchType, String searchQuery) {
        int offset = (page - 1) * size;
        return userDAO.getUserList(offset, size, searchType, searchQuery);
    }

    @Override
    public int getUserCount(String searchType, String searchQuery) {
        return userDAO.getUserCount(searchType, searchQuery);
    }


}

package com.study.work.admin.menu.svc.impl;

import com.study.work.admin.menu.dao.MenuDAO;
import com.study.work.admin.menu.dto.MenuDTO;
import com.study.work.admin.menu.svc.MenuSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuSVCImpl implements MenuSVC {

    @Autowired
    private MenuDAO menuDAO;

    @Override
    public List<MenuDTO> getMenuList() {
        return menuDAO.getMenuList();
    }
}

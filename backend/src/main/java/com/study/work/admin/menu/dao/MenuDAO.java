package com.study.work.admin.menu.dao;
import com.study.work.admin.menu.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuDAO {

    List<MenuDTO> getMenuList();
}
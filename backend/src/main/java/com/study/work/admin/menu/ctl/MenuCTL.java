package com.study.work.admin.menu.ctl;

import com.study.work.admin.menu.dto.MenuDTO;
import com.study.work.admin.menu.svc.MenuSVC;
import com.study.work.common.dto.CommonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common")
@Tag(name = "시스템 메뉴 목록 조회", description = "대시보드, 게시판, QnA 등을 조회하는 API")
public class MenuCTL {

    @Autowired
    private MenuSVC menuSVC;

    @Operation(summary = "시스템 목록 조회", description = "대시보드, 게시판, QnA 등을 조회하는 API")
    @PostMapping("/getMenuList")
    public CommonResponseDTO<List<MenuDTO>> getMenus() {
        List<MenuDTO> menuList = menuSVC.getMenuList();
        return new CommonResponseDTO<>(menuList);
    }
}

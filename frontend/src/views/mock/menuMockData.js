/**
 * 메뉴 목업 데이터
 * DB의 TB_MENU 테이블 구조와 동일
 */

// 메뉴 목업 데이터
export const menuMockData = [
  {
    menuId: 1,
    parentId: null,
    name: 'dashboard',
    title: '대시보드',
    icon: 'fas fa-tachometer-alt',
    description: '대시보드',
    component: 'Dashboard',
    orderNo: 1,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  },
  {
    menuId: 2,
    parentId: null,
    name: 'board',
    title: '게시판',
    icon: 'fas fa-clipboard-list',
    description: '나중에 사용자 질문 관리 할지 생각 중',
    component: 'Board',
    orderNo: 2,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  },
  {
    menuId: 3,
    parentId: null,
    name: 'qna',
    title: 'Q&A',
    icon: 'fas fa-question-circle',
    description: '학생 질문과 답변을 관리하세요',
    component: 'QnA',
    orderNo: 3,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  },
  {
    menuId: 4,
    parentId: null,
    name: 'users',
    title: '사용자 관리',
    icon: 'fas fa-users',
    description: '사용자 관리를 하세요',
    component: 'Users',
    orderNo: 4,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  },
  {
    menuId: 5,
    parentId: null,
    name: 'courses',
    title: '강의',
    icon: 'fas fa-book',
    description: '강의를 관리하세요',
    component: 'Courses',
    orderNo: 5,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  },
  {
    menuId: 6,
    parentId: null,
    name: 'analytics',
    title: '분석',
    icon: 'fas fa-chart-line',
    description: '데이터를 분석하세요',
    component: 'Analytics',
    orderNo: 6,
    isActive: 'Y',
    createdDttm: '2025-09-22 08:34:35.000',
    updatedDttm: '2025-09-22 08:34:35.000'
  }
]

/**
 * 메뉴 목록 조회 (API 응답 형태)
 * @param {Object} requestData - 요청 데이터
 * @param {string} requestData.isActive - 활성화 여부 ('Y'/'N')
 * @param {number} requestData.parentId - 부모 메뉴 ID (선택사항)
 * @returns {Promise<Object>} API 응답 형태의 메뉴 목록
 */
export const getMenuMockData = (requestData = {}) => {
  let filteredMenus = [...menuMockData]
  
  // isActive 필터링
  if (requestData.isActive) {
    filteredMenus = filteredMenus.filter(menu => menu.isActive === requestData.isActive)
  }
  
  // parentId 필터링 (하위 메뉴 조회)
  if (requestData.parentId !== undefined) {
    filteredMenus = filteredMenus.filter(menu => menu.parentId === requestData.parentId)
  }
  
  // orderNo 순으로 정렬
  filteredMenus.sort((a, b) => a.orderNo - b.orderNo)
  
  return Promise.resolve({
    body: {
      menuList: filteredMenus,
      totalCount: filteredMenus.length,
      success: true,
      message: '메뉴 목록 조회 성공'
    }
  })
}

/**
 * 메뉴 상세 조회
 * @param {number} menuId - 메뉴 ID
 * @returns {Promise<Object>} 메뉴 상세 정보
 */
export const getMenuDetailMockData = (menuId) => {
  const menu = menuMockData.find(m => m.menuId === menuId)
  
  if (!menu) {
    return Promise.resolve({
      body: {
        success: false,
        message: '메뉴를 찾을 수 없습니다'
      }
    })
  }
  
  return Promise.resolve({
    body: {
      menu: menu,
      success: true,
      message: '메뉴 상세 조회 성공'
    }
  })
}

/**
 * 메뉴 생성
 * @param {Object} menuData - 생성할 메뉴 데이터
 * @returns {Promise<Object>} 생성 결과
 */
export const createMenuMockData = (menuData) => {
  const newMenuId = Math.max(...menuMockData.map(m => m.menuId)) + 1
  const now = new Date().toISOString().replace('T', ' ').substring(0, 23)
  
  const newMenu = {
    menuId: newMenuId,
    parentId: menuData.parentId || null,
    name: menuData.name,
    title: menuData.title,
    icon: menuData.icon,
    description: menuData.description,
    component: menuData.component,
    orderNo: menuData.orderNo || 999,
    isActive: menuData.isActive || 'Y',
    createdDttm: now,
    updatedDttm: now
  }
  
  menuMockData.push(newMenu)
  
  return Promise.resolve({
    body: {
      menu: newMenu,
      success: true,
      message: '메뉴가 성공적으로 생성되었습니다'
    }
  })
}

/**
 * 메뉴 수정
 * @param {number} menuId - 수정할 메뉴 ID
 * @param {Object} menuData - 수정할 메뉴 데이터
 * @returns {Promise<Object>} 수정 결과
 */
export const updateMenuMockData = (menuId, menuData) => {
  const menuIndex = menuMockData.findIndex(m => m.menuId === menuId)
  
  if (menuIndex === -1) {
    return Promise.resolve({
      body: {
        success: false,
        message: '메뉴를 찾을 수 없습니다'
      }
    })
  }
  
  const now = new Date().toISOString().replace('T', ' ').substring(0, 23)
  
  menuMockData[menuIndex] = {
    ...menuMockData[menuIndex],
    ...menuData,
    menuId: menuId, // ID는 변경하지 않음
    updatedDttm: now
  }
  
  return Promise.resolve({
    body: {
      menu: menuMockData[menuIndex],
      success: true,
      message: '메뉴가 성공적으로 수정되었습니다'
    }
  })
}

/**
 * 메뉴 삭제
 * @param {number} menuId - 삭제할 메뉴 ID
 * @returns {Promise<Object>} 삭제 결과
 */
export const deleteMenuMockData = (menuId) => {
  const menuIndex = menuMockData.findIndex(m => m.menuId === menuId)
  
  if (menuIndex === -1) {
    return Promise.resolve({
      body: {
        success: false,
        message: '메뉴를 찾을 수 없습니다'
      }
    })
  }
  
  // 하위 메뉴가 있는지 확인
  const hasChildren = menuMockData.some(m => m.parentId === menuId)
  if (hasChildren) {
    return Promise.resolve({
      body: {
        success: false,
        message: '하위 메뉴가 있는 메뉴는 삭제할 수 없습니다'
      }
    })
  }
  
  menuMockData.splice(menuIndex, 1)
  
  return Promise.resolve({
    body: {
      success: true,
      message: '메뉴가 성공적으로 삭제되었습니다'
    }
  })
}

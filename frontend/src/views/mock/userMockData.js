/**
 * 사용자 목업 데이터
 * Users.vue에서 사용하는 테스트 데이터
 */

// 역할 목록 목업 데이터
export const roleListMockData = {
  "httpStatusCode": 200,
  "code": "20000000",
  "message": "정상 처리 되었습니다.",
  "body": [
    {
      "roleId": "INSTRUCTOR",
      "roleName": "강사",
      "isActive": null
    },
    {
      "roleId": "ADMIN",
      "roleName": "관리자",
      "isActive": null
    },
    {
      "roleId": "STUDENT",
      "roleName": "학생",
      "isActive": null
    }
  ]
}

export const userMockData = {
  "httpStatusCode": 200,
  "code": "20000000",
  "message": "정상 처리 되었습니다.",
  "body": {
    "userList": [
      {
        "userSeq": 1,
        "userId": "hong123",
        "userName": "홍길동",
        "email": "hong@example.com",
        "roleId": "ADMIN",
        "roleName": "어드민",
        "createdDttm": "2024-01-15T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 2,
        "userId": "kim456",
        "userName": "김철수",
        "email": "kim@example.com",
        "roleId": "INSTRUCTOR",
        "roleName": "강사",
        "createdDttm": "2024-02-20T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 3,
        "userId": "lee789",
        "userName": "이영희",
        "email": "lee@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-03-10T00:00:00",
        "isActive": "비활성"
      },
      {
        "userSeq": 4,
        "userId": "park012",
        "userName": "박민수",
        "email": "park@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-03-15T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 5,
        "userId": "choi345",
        "userName": "최영수",
        "email": "choi@example.com",
        "roleId": "INSTRUCTOR",
        "roleName": "강사",
        "createdDttm": "2024-03-20T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 6,
        "userId": "jung678",
        "userName": "정민호",
        "email": "jung@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 7,
        "userId": "yoon901",
        "userName": "윤서연",
        "email": "yoon@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-05T00:00:00",
        "isActive": "비활성"
      },
      {
        "userSeq": 8,
        "userId": "admin001",
        "userName": "관리자",
        "email": "admin@example.com",
        "roleId": "ADMIN",
        "roleName": "어드민",
        "createdDttm": "2024-01-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 9,
        "userId": "kang234",
        "userName": "강지훈",
        "email": "kang@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-10T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 10,
        "userId": "oh567",
        "userName": "오수진",
        "email": "oh@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-15T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 11,
        "userId": "teacher002",
        "userName": "김선생",
        "email": "teacher@example.com",
        "roleId": "INSTRUCTOR",
        "roleName": "강사",
        "createdDttm": "2024-02-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 12,
        "userId": "lim890",
        "userName": "임동현",
        "email": "lim@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-20T00:00:00",
        "isActive": "비활성"
      },
      {
        "userSeq": 13,
        "userId": "han123",
        "userName": "한미영",
        "email": "han@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-04-25T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 14,
        "userId": "song456",
        "userName": "송태현",
        "email": "song@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 15,
        "userId": "ryu789",
        "userName": "류정호",
        "email": "ryu@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-05T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 16,
        "userId": "teacher003",
        "userName": "박선생",
        "email": "teacher2@example.com",
        "roleId": "INSTRUCTOR",
        "roleName": "강사",
        "createdDttm": "2024-02-15T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 17,
        "userId": "kwon012",
        "userName": "권소영",
        "email": "kwon@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-10T00:00:00",
        "isActive": "비활성"
      },
      {
        "userSeq": 18,
        "userId": "jang345",
        "userName": "장현우",
        "email": "jang@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-15T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 19,
        "userId": "shin678",
        "userName": "신예린",
        "email": "shin@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-20T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 20,
        "userId": "moon901",
        "userName": "문재성",
        "email": "moon@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-05-25T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 21,
        "userId": "teacher004",
        "userName": "이선생",
        "email": "teacher3@example.com",
        "roleId": "INSTRUCTOR",
        "roleName": "강사",
        "createdDttm": "2024-03-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 22,
        "userId": "baek234",
        "userName": "백지은",
        "email": "baek@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-06-01T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 23,
        "userId": "jo567",
        "userName": "조민수",
        "email": "jo@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-06-05T00:00:00",
        "isActive": "비활성"
      },
      {
        "userSeq": 24,
        "userId": "yoo890",
        "userName": "유하늘",
        "email": "yoo@example.com",
        "roleId": "STUDENT",
        "roleName": "학생",
        "createdDttm": "2024-06-10T00:00:00",
        "isActive": "활성"
      },
      {
        "userSeq": 25,
        "userId": "admin002",
        "userName": "시스템관리자",
        "email": "system@example.com",
        "roleId": "ADMIN",
        "roleName": "어드민",
        "createdDttm": "2024-01-10T00:00:00",
        "isActive": "활성"
      }
    ],
    "pagination": {
      "totalElements": 50,
      "totalPages": 5
    }
  }
}

/**
 * 사용자 목업 데이터를 반환하는 함수
 * @param {Object} requestData - 요청 데이터 객체
 * @param {number} requestData.page - 페이지 번호
 * @param {number} requestData.size - 페이지 크기
 * @param {string} requestData.searchType - 검색 타입
 * @param {string} requestData.searchQuery - 검색어
 * @returns {Object} 페이지네이션된 사용자 목록
 */
export const getUserMockData = (requestData = {}) => {
  const { page = 1, size = 10, searchType = 'all', searchQuery = '' } = requestData
  let filteredUsers = [...userMockData.body.userList]
  
  // 검색 필터링
  if (searchQuery) {
    filteredUsers = filteredUsers.filter(user => {
      switch (searchType) {
        case 'name':
          return user.userName.toLowerCase().includes(searchQuery.toLowerCase())
        case 'email':
          return user.email.toLowerCase().includes(searchQuery.toLowerCase())
        case 'role':
          return user.roleName.toLowerCase().includes(searchQuery.toLowerCase())
        case 'all':
        default:
          return user.userName.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 user.email.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 user.roleName.toLowerCase().includes(searchQuery.toLowerCase())
      }
    })
  }
  
  // 페이지네이션 계산
  const totalElements = filteredUsers.length
  const totalPages = Math.ceil(totalElements / size)
  const startIndex = (page - 1) * size
  const endIndex = startIndex + size
  const paginatedUsers = filteredUsers.slice(startIndex, endIndex)
  
  return {
    ...userMockData,
    body: {
      userList: paginatedUsers,
      pagination: {
        totalElements,
        totalPages
      }
    }
  }
}

/**
 * 사용자 수정 목업 데이터를 반환하는 함수
 * @param {Object} userData - 수정할 사용자 데이터
 * @returns {Object} 수정 결과
 */
export const updateUserMockData = (userData) => {
  // 실제 목업 데이터에서 해당 사용자 찾기
  const userIndex = userMockData.body.userList.findIndex(user => user.userSeq === userData.userSeq)
  
  if (userIndex === -1) {
    return {
      httpStatusCode: 404,
      code: "40400001",
      message: "사용자를 찾을 수 없습니다.",
      body: {
        success: false,
        message: "해당 사용자가 존재하지 않습니다."
      }
    }
  }
  
  // 사용자 데이터 업데이트
  const updatedUser = {
    ...userMockData.body.userList[userIndex],
    userName: userData.userName,
    email: userData.email,
    roleId: userData.roleId,
    roleName: userData.roleName,
    isActive: userData.isActive === 1 ? '활성' : '비활성',
    updatedDttm: new Date().toISOString()
  }
  
  // 목업 데이터 업데이트
  userMockData.body.userList[userIndex] = updatedUser
  
  return {
    httpStatusCode: 200,
    code: "20000000",
    message: "정상 처리 되었습니다.",
    body: {
      success: true,
      message: "사용자가 수정되었습니다.",
      userSeq: updatedUser.userSeq,
      userId: updatedUser.userId,
      userName: updatedUser.userName,
      email: updatedUser.email,
      roleId: updatedUser.roleId,
      roleName: updatedUser.roleName,
      isActive: updatedUser.isActive,
      updatedDttm: updatedUser.updatedDttm
    }
  }
}

/**
 * 사용자 등록 목업 데이터를 반환하는 함수
 * @param {Object} userData - 등록할 사용자 데이터
 * @returns {Object} 등록 결과
 */
export const createUserMockData = (userData) => {
  // 새로운 사용자 시퀀스 생성
  const newUserSeq = Math.max(...userMockData.body.userList.map(user => user.userSeq)) + 1
  
  // 새 사용자 데이터 생성
  const newUser = {
    userSeq: newUserSeq,
    userId: userData.userId,
    userName: userData.userName,
    email: userData.email,
    roleId: userData.roleId,
    roleName: userData.roleName,
    isActive: userData.isActive === 1 ? '활성' : '비활성',
    createdDttm: new Date().toISOString()
  }
  
  // 목업 데이터에 추가
  userMockData.body.userList.push(newUser)
  
  return {
    httpStatusCode: 200,
    code: "20000000",
    message: "정상 처리 되었습니다.",
    body: {
      success: true,
      message: "사용자가 등록되었습니다.",
      userSeq: newUser.userSeq,
      userId: newUser.userId,
      userName: newUser.userName,
      email: newUser.email,
      roleId: newUser.roleId,
      roleName: newUser.roleName,
      isActive: newUser.isActive,
      createdDttm: newUser.createdDttm
    }
  }
}

/**
 * 사용자 삭제 목업 데이터
 */
export const deleteUserMockData = (userId) => {
  try {
    // 사용자 찾기
    const userIndex = userMockData.body.userList.findIndex(user => user.userId === userId)
    
    if (userIndex === -1) {
      return {
        httpStatusCode: 404,
        code: '40400000',
        message: '사용자를 찾을 수 없습니다.',
        body: null
      }
    }

    // 사용자 삭제
    const deletedUser = userMockData.body.userList.splice(userIndex, 1)[0]

    // pagination 업데이트
    userMockData.body.pagination.totalElements = userMockData.body.userList.length
    userMockData.body.pagination.totalPages = Math.ceil(userMockData.body.userList.length / 10)

    return {
      httpStatusCode: 200,
      code: '20000000',
      message: '사용자가 성공적으로 삭제되었습니다.',
      body: {
        success: true,
        message: '사용자가 삭제되었습니다.',
        userId: deletedUser.userId,
        userName: deletedUser.userName
      }
    }
  } catch (error) {
    return {
      httpStatusCode: 500,
      code: '50000000',
      message: '사용자 삭제 중 오류가 발생했습니다.',
      body: null
    }
  }
}

/**
 * 게시판 목업 데이터
 * Board.vue에서 사용하는 테스트 데이터
 */

export const boardMockData = {
  "httpStatusCode": 200,
  "code": "20000000",
  "message": "정상 처리 되었습니다.",
  "body": {
    "boardList": [
      {
        "boardId": 1,
        "title": "Spring Boot 기초 강의",
        "content": "스프링 부트 기본 개념과 프로젝트 생성 방법을 다룹니다.",
        "writer": "홍길동",
        "viewCount": 15,
        "createdDttm": "2025-09-16T15:49:29",
        "updatedDttm": "2025-09-16T15:49:29",
        "isDeleted": "0"
      },
      {
        "boardId": 2,
        "title": "Vue.js 컴포넌트 구조 이해하기",
        "content": "Vue.js의 컴포넌트 구조와 생명주기에 대해 알아보겠습니다.",
        "writer": "김철수",
        "viewCount": 23,
        "createdDttm": "2025-09-15T10:30:00",
        "updatedDttm": "2025-09-15T10:30:00",
        "isDeleted": "0"
      },
      {
        "boardId": 3,
        "title": "React와 Vue.js의 차이점은 무엇인가요?",
        "content": "두 프레임워크의 장단점을 비교해보고 싶습니다.",
        "writer": "이영희",
        "viewCount": 45,
        "createdDttm": "2025-09-14T14:20:00",
        "updatedDttm": "2025-09-14T14:20:00",
        "isDeleted": "0"
      },
      {
        "boardId": 4,
        "title": "JavaScript ES6 문법 정리",
        "content": "ES6의 주요 문법들을 정리해보았습니다.",
        "writer": "박민수",
        "viewCount": 67,
        "createdDttm": "2025-09-13T09:15:00",
        "updatedDttm": "2025-09-13T09:15:00",
        "isDeleted": "0"
      },
      {
        "boardId": 5,
        "title": "프로젝트 과제 제출 방법",
        "content": "프로젝트 과제를 어떻게 제출해야 하는지 궁금합니다.",
        "writer": "최영수",
        "viewCount": 8,
        "createdDttm": "2025-09-12T16:45:00",
        "updatedDttm": "2025-09-12T16:45:00",
        "isDeleted": "0"
      },
      {
        "boardId": 6,
        "title": "코딩 테스트 준비 방법",
        "content": "취업을 위한 코딩 테스트 준비 방법을 공유합니다.",
        "writer": "정민호",
        "viewCount": 89,
        "createdDttm": "2025-09-11T11:30:00",
        "updatedDttm": "2025-09-11T11:30:00",
        "isDeleted": "0"
      },
      {
        "boardId": 7,
        "title": "데이터베이스 설계 원칙",
        "content": "효율적인 데이터베이스 설계를 위한 기본 원칙들을 설명합니다.",
        "writer": "윤서연",
        "viewCount": 34,
        "createdDttm": "2025-09-10T13:20:00",
        "updatedDttm": "2025-09-10T13:20:00",
        "isDeleted": "0"
      },
      {
        "boardId": 8,
        "title": "API 설계 모범 사례",
        "content": "RESTful API 설계 시 고려해야 할 모범 사례들을 정리했습니다.",
        "writer": "홍길동",
        "viewCount": 56,
        "createdDttm": "2025-09-09T15:10:00",
        "updatedDttm": "2025-09-09T15:10:00",
        "isDeleted": "0"
      }
    ],
    "pagination": {
      "totalElements": 50,
      "totalPages": 5
    }
  }
}

/**
 * 게시판 목업 데이터를 반환하는 함수
 * @param {Object} requestData - 요청 데이터 객체
 * @param {number} requestData.page - 페이지 번호
 * @param {number} requestData.size - 페이지 크기
 * @param {string} requestData.searchType - 검색 타입
 * @param {string} requestData.searchQuery - 검색어
 * @returns {Object} 페이지네이션된 게시글 목록
 */
export const getBoardMockData = (requestData = {}) => {
  const { page = 1, size = 10, searchType = 'all', searchQuery = '' } = requestData
  let filteredBoards = [...boardMockData.body.boardList]
  
  // 검색 필터링
  if (searchQuery) {
    filteredBoards = filteredBoards.filter(board => {
      switch (searchType) {
        case 'title':
          return board.title.toLowerCase().includes(searchQuery.toLowerCase())
        case 'content':
          return board.content.toLowerCase().includes(searchQuery.toLowerCase())
        case 'writer':
          return board.writer.toLowerCase().includes(searchQuery.toLowerCase())
        case 'all':
        default:
          return board.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 board.content.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 board.writer.toLowerCase().includes(searchQuery.toLowerCase())
      }
    })
  }
  
  // 페이지네이션 계산
  const totalElements = filteredBoards.length
  const totalPages = Math.ceil(totalElements / size)
  const startIndex = (page - 1) * size
  const endIndex = startIndex + size
  const paginatedBoards = filteredBoards.slice(startIndex, endIndex)
  
  return {
    ...boardMockData,
    body: {
      boardList: paginatedBoards,
      pagination: {
        totalElements,
        totalPages
      }
    }
  }
}

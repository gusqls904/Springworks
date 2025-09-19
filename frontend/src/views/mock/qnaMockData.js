/**
 * Q&A 목업 데이터
 * QnA.vue에서 사용하는 테스트 데이터
 */

export const qnaMockData = {
  "httpStatusCode": 200,
  "code": "20000000",
  "message": "정상 처리 되었습니다.",
  "body": {
    "qnaList": [
      {
        "qnaId": 1,
        "title": "Vue.js 컴포넌트 구조에 대해 질문드립니다",
        "content": "Vue.js에서 컴포넌트를 어떻게 구조화하는 것이 좋을까요? 단일 파일 컴포넌트와 분리된 파일 구조 중 어떤 것을 추천하시나요?",
        "writer": "김학생",
        "courseName": "Vue.js 기초",
        "status": "pending",
        "createdDttm": "2024-01-15T10:30:00",
        "answer": null,
        "answerer": null,
        "answeredDttm": null
      },
      {
        "qnaId": 2,
        "title": "React Hooks 사용법이 궁금합니다",
        "content": "useState와 useEffect를 언제 사용해야 하는지, 그리고 커스텀 훅을 만드는 방법을 알고 싶습니다.",
        "writer": "이학생",
        "courseName": "React 심화",
        "status": "completed",
        "createdDttm": "2024-01-14T14:20:00",
        "answer": "useState는 컴포넌트의 상태를 관리할 때 사용하고, useEffect는 사이드 이펙트를 처리할 때 사용합니다. 커스텀 훅은 'use'로 시작하는 함수를 만들어 재사용 가능한 로직을 분리할 수 있습니다.",
        "answerer": "관리자",
        "answeredDttm": "2024-01-14T16:30:00"
      },
      {
        "qnaId": 3,
        "title": "JavaScript ES6 문법에서 화살표 함수의 this 바인딩",
        "content": "화살표 함수에서 this가 어떻게 바인딩되는지 궁금합니다. 일반 함수와 어떤 차이가 있나요?",
        "writer": "박학생",
        "courseName": "JavaScript ES6",
        "status": "pending",
        "createdDttm": "2024-01-13T09:15:00",
        "answer": null,
        "answerer": null,
        "answeredDttm": null
      },
      {
        "qnaId": 4,
        "title": "프로젝트 과제 제출 방법",
        "content": "프로젝트 과제를 어떻게 제출해야 하는지 궁금합니다. GitHub에 올려야 하나요?",
        "writer": "최학생",
        "courseName": "Vue.js 기초",
        "status": "completed",
        "createdDttm": "2024-01-12T16:45:00",
        "answer": "네, GitHub에 프로젝트를 업로드하고 링크를 제출해주세요. README.md 파일에 프로젝트 설명과 실행 방법을 포함해주시면 됩니다.",
        "answerer": "관리자",
        "answeredDttm": "2024-01-12T18:00:00"
      },
      {
        "qnaId": 5,
        "title": "코딩 테스트 준비 방법",
        "content": "취업을 위한 코딩 테스트 준비 방법을 알고 싶습니다. 어떤 사이트를 추천하시나요?",
        "writer": "정학생",
        "courseName": "JavaScript ES6",
        "status": "pending",
        "createdDttm": "2024-01-11T11:30:00",
        "answer": null,
        "answerer": null,
        "answeredDttm": null
      },
      {
        "qnaId": 6,
        "title": "Vue Router 사용법",
        "content": "Vue Router를 사용해서 페이지 간 이동을 구현하고 싶습니다. 기본적인 설정 방법을 알려주세요.",
        "writer": "한학생",
        "courseName": "Vue.js 기초",
        "status": "completed",
        "createdDttm": "2024-01-10T15:20:00",
        "answer": "Vue Router를 설치하고 main.js에서 router를 등록한 후, routes 배열에 경로와 컴포넌트를 정의하면 됩니다. router-view 태그를 사용해서 컴포넌트를 렌더링할 수 있습니다.",
        "answerer": "관리자",
        "answeredDttm": "2024-01-10T17:45:00"
      },
      {
        "qnaId": 7,
        "title": "React에서 상태 관리 라이브러리",
        "content": "Redux와 Context API 중 어떤 것을 사용해야 할까요? 프로젝트 규모에 따라 다를까요?",
        "writer": "송학생",
        "courseName": "React 심화",
        "status": "pending",
        "createdDttm": "2024-01-09T13:10:00",
        "answer": null,
        "answerer": null,
        "answeredDttm": null
      },
      {
        "qnaId": 8,
        "title": "JavaScript 비동기 처리",
        "content": "Promise와 async/await의 차이점과 언제 사용해야 하는지 궁금합니다.",
        "writer": "류학생",
        "courseName": "JavaScript ES6",
        "status": "completed",
        "createdDttm": "2024-01-08T11:45:00",
        "answer": "Promise는 비동기 작업의 결과를 나타내는 객체이고, async/await는 Promise를 더 쉽게 사용할 수 있게 해주는 문법입니다. async/await가 더 읽기 쉽고 에러 처리가 간편합니다.",
        "answerer": "관리자",
        "answeredDttm": "2024-01-08T14:20:00"
      },
      {
        "qnaId": 9,
        "title": "Vue.js에서 폼 유효성 검사",
        "content": "사용자 입력 폼의 유효성 검사를 어떻게 구현하면 좋을까요? 라이브러리 추천도 부탁드립니다.",
        "writer": "권학생",
        "courseName": "Vue.js 기초",
        "status": "pending",
        "createdDttm": "2024-01-07T16:30:00",
        "answer": null,
        "answerer": null,
        "answeredDttm": null
      },
      {
        "qnaId": 10,
        "title": "React 컴포넌트 최적화",
        "content": "React에서 불필요한 리렌더링을 방지하는 방법을 알고 싶습니다. memo, useMemo, useCallback의 차이점도 궁금합니다.",
        "writer": "장학생",
        "courseName": "React 심화",
        "status": "completed",
        "createdDttm": "2024-01-06T14:15:00",
        "answer": "React.memo는 컴포넌트를 메모이제이션하고, useMemo는 값을 메모이제이션하며, useCallback은 함수를 메모이제이션합니다. 성능 최적화가 필요한 경우에만 사용하는 것이 좋습니다.",
        "answerer": "관리자",
        "answeredDttm": "2024-01-06T16:00:00"
      }
    ],
    "pagination": {
      "totalElements": 10,
      "totalPages": 1
    }
  }
}

/**
 * Q&A 목업 데이터를 반환하는 함수
 * @param {Object} requestData - 요청 데이터 객체
 * @param {number} requestData.page - 페이지 번호
 * @param {number} requestData.size - 페이지 크기
 * @param {string} requestData.searchType - 검색 타입
 * @param {string} requestData.searchQuery - 검색어
 * @param {string} requestData.statusFilter - 상태 필터
 * @param {string} requestData.courseFilter - 강의 필터
 * @returns {Object} 페이지네이션된 Q&A 목록
 */
export const getQnAMockData = (requestData = {}) => {
  const { 
    page = 1, 
    size = 10, 
    searchType = 'all', 
    searchQuery = '', 
    statusFilter = 'all',
    courseFilter = 'all'
  } = requestData
  
  let filteredQnAs = [...qnaMockData.body.qnaList]
  
  // 검색 필터링
  if (searchQuery) {
    filteredQnAs = filteredQnAs.filter(qna => {
      switch (searchType) {
        case 'title':
          return qna.title.toLowerCase().includes(searchQuery.toLowerCase())
        case 'content':
          return qna.content.toLowerCase().includes(searchQuery.toLowerCase())
        case 'writer':
          return qna.writer.toLowerCase().includes(searchQuery.toLowerCase())
        case 'all':
        default:
          return qna.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 qna.content.toLowerCase().includes(searchQuery.toLowerCase()) ||
                 qna.writer.toLowerCase().includes(searchQuery.toLowerCase())
      }
    })
  }
  
  // 상태 필터링
  if (statusFilter !== 'all') {
    filteredQnAs = filteredQnAs.filter(qna => qna.status === statusFilter)
  }
  
  // 강의 필터링
  if (courseFilter !== 'all') {
    const courseMap = {
      'vue': 'Vue.js 기초',
      'react': 'React 심화',
      'js': 'JavaScript ES6'
    }
    const courseName = courseMap[courseFilter]
    if (courseName) {
      filteredQnAs = filteredQnAs.filter(qna => qna.courseName === courseName)
    }
  }
  
  // 페이지네이션 계산
  const totalElements = filteredQnAs.length
  const totalPages = Math.ceil(totalElements / size)
  const startIndex = (page - 1) * size
  const endIndex = startIndex + size
  const paginatedQnAs = filteredQnAs.slice(startIndex, endIndex)
  
  return {
    ...qnaMockData,
    body: {
      qnaList: paginatedQnAs,
      pagination: {
        totalElements,
        totalPages
      }
    }
  }
}

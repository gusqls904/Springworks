/**
 * 목업 데이터 인덱스 파일
 * 모든 목업 데이터를 한 곳에서 관리
 */

// 사용자 목업 데이터
export { userMockData, getUserMockData, updateUserMockData, createUserMockData, deleteUserMockData, roleListMockData } from './userMockData.js'

// 게시판 목업 데이터
export { boardMockData, getBoardMockData } from './boardMockData.js'

// Q&A 목업 데이터
export { qnaMockData, getQnAMockData } from './qnaMockData.js'

// 목업 데이터 전체 내보내기
export const mockData = {
  user: {
    data: userMockData,
    getData: getUserMockData
  },
  board: {
    data: boardMockData,
    getData: getBoardMockData
  },
  qna: {
    data: qnaMockData,
    getData: getQnAMockData
  },
  role: {
    data: roleListMockData
  }
}

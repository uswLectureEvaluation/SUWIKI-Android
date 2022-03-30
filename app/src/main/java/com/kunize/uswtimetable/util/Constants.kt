package com.kunize.uswtimetable.util

object Constants {
    const val TAG = "로그"
    const val ID_COUNT_LOWER_LIMIT = 6
    const val PW_COUNT_LOWER_LIMIT = 8
    const val ID_COUNT_LIMIT = 20
    const val PW_COUNT_LIMIT = 24
    const val SCHOOL_DOMAIN = "suwon.ac.kr"
    const val SCHOOL_DOMAIN_AT = "@suwon.ac.kr"
    const val SCHOOL_HOMEPAGE = "https://portal.suwon.ac.kr"
    const val NUMBER_OF_YEAR = 6
    const val SEMESTER_1_START = 1
    const val SEMESTER_1_END = 6
    const val ID_REGEX = """^[a-z0-9]*$"""
    const val PW_REGEX = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""

}

object API {
    const val BASE_URL: String = "https://api.suwiki.kr"
    const val REQUEST_REFRESH: String = "/user/refresh"
    const val NOTICE_LIST: String = "/notice/findAllList"
    const val NOTICE: String = "/notice"
    const val SEARCH: String = "/lecture/findBySearchValue"
    const val EXAM: String = "/lecture/exam"
    const val PASSWORD: String = "/user/find-pw"
    const val PASSWORD_RESET: String = "/user/reset-pw"
    const val LOGIN: String = "/user/login"
    const val QUIT: String = "/user/quit"
    const val MY_PAGE: String = "/user/my-page"
    const val EVALUATE_POST: String = "/evaluate-posts/findByUserIdx"
    const val UPDATE_EVALUATE_POST: String = "/evaluate-posts/update"
    const val EXAM_POSTS: String = "/exam-posts/findByUserIdx"
    const val UPDATE_EXAM_POSTS: String = "/exam-posts/findByUserIdx"
    const val SIGN_UP: String = "/user/join"
    const val SIGN_UP_ID_CHECK: String = "/user/check-id"
    const val SIGN_UP_EMAIL_CHECK: String = "/user/check-email"
    const val SIGN_UP_SCHOOL_CHECK: String = "/user/verify-email"
    const val LECTURE_MAIN: String = "/lecture/findAllList"
    const val LECTURE_DETAIL_INFO = "/lecture"
    const val LECTURE_DETAIL_EVALUATION = "/evaluate-posts/findByLectureId"
    const val LECTURE_DETAIL_EXAM = "/exam-posts/findByLectureId"
    const val WRITE_LECTURE_EVALUATION = "/evaluate-posts/write/"
}

object WriteFragmentTitle {
    const val EDIT_MY_EVALUATION = "강의평가 수정"
    const val EDIT_MY_EXAM = "시험정보 수정"
    const val WRITE_EVALUATION = "강의평가 쓰기"
    const val WRITE_EXAM = "시험정보 쓰기"
    const val FINISH_EXAM = "완료 (+20p)"
    const val FINISH_EVALUATION = "완료 (+10p)"
    const val FINISH_EDIT = "완료"
}

object LectureItemViewType {
    const val SHORT = 0 //강의평가 목록
    const val USER_LECTURE = 1 //내가 쓴 강의평가
    const val USER_EXAM = 2 //내가 쓴 시험정보
    const val LECTURE = 3 //세부 강의평가 정보
    const val EXAM = 4 //세부 시험정보
    const val HIDE_EXAM = 5 //세부 시험정보 가림 (content 텍스트 색상 변경 및 report 버튼 클릭 방지
}

object TextLength {
    const val MIN_SEARCH_TEXT_LENGTH = 2
}

object ExamInfoType {
    const val NO_DATA = 0 //데이터 없음
    const val NEED_USE = 1 //포인트 사용 필요
    const val NOT_INFLATE = 2 //포인트 이미 사용 OR 강의평가 쓰기
}

object LectureApiOption {
    const val MODIFIED = "modifiedDate"
    const val SATISFACTION = "lectureSatisfactionAvg"
    const val HONEY = "lectureHoneyAvg"
    const val LEARNING = "lectureLearningAvg"
    const val BEST = "lectureTotalAvg"
}

enum class ResponseState {
    OK, FAIL
}

enum class ItemType {
    ROOT_VIEW,
    EDIT_BUTTON,
    DELETE_BUTTON
}

enum class ExamDifficulty {
    VERY_EASY,
    EASY,
    NORMAL,
    DIFFICULT,
    VERY_DIFFICULT
}

//무한 스크롤
const val LAST_PAGE = 0
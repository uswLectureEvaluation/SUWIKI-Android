package com.kunize.uswtimetable.util

import com.kunize.uswtimetable.util.Constants.TOKEN

object Constants {
    const val TAG = "로그"
    const val TOKEN = "token"
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
}

object API {
    const val BASE_URL: String = "https://61dc5273591c3a0017e1a86b.mockapi.io"
    const val NOTICE: String = "/notices"
    const val LECTURE: String = "/lecture"
    const val EXAM: String = "/lecture/exam"
    const val PASSWORD: String = "/pwinquiry"
    const val MY_PAGE: String = "/{$TOKEN}/mypage"
    const val SIGN_UP: String = "/user/join"
    const val SIGN_UP_ID_CHECK: String = "/signup_id_check"
    const val SIGN_UP_EMAIL_CHECK: String = "/signup_email_check"
    const val SIGN_UP_SCHOOL_CHECK: String = "/signup_check"
    const val SEND_CERT_NUMBER: String =  "/send_email"
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

object ExamInfoType {
    const val NO_DATA = 0 //데이터 없음
    const val NEED_USE = 1 //포인트 사용 필요
    const val NOT_INFLATE = 2 //포인트 이미 사용 OR 강의평가 쓰기
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
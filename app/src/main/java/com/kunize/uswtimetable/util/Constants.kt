package com.kunize.uswtimetable.util

import android.graphics.Color

object Constants {
    const val TAG = "로그"
    const val ID_COUNT_LOWER_LIMIT = 6
    const val PW_COUNT_LOWER_LIMIT = 8
    const val ID_COUNT_LIMIT = 20
    const val PW_COUNT_LIMIT = 24
    const val SCHOOL_DOMAIN_AT = "@suwon.ac.kr"
    const val SCHOOL_HOMEPAGE = "https://portal.suwon.ac.kr/enview/"
    const val ASK_EMAIL = "suwikiask@gmail.com"
    const val FEEDBACK_SITE = "https://forms.gle/tZByKoN6rJCysvNz6"
    const val TERMS_SITE = "https://sites.google.com/view/suwiki-policy-terms/"
    const val PRIVACY_POLICY_SITE = "https://sites.google.com/view/suwiki-policy-privacy"
    const val NUMBER_OF_YEAR = 6
    const val SEMESTER_1_START = 1
    const val SEMESTER_1_END = 6
    const val ID_REGEX = """^[a-z0-9]*$"""
    const val PW_REGEX = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""

    // Key of Intent or Bundle
    const val KEY_NOTICE_ID = "notice_id"
    const val KEY_URL = "url"
    const val KEY_EMAIL = "register_email"
}

object API {
    const val BASE_URL: String = "https://api.suwiki.kr"
    const val REQUEST_REFRESH: String = "/user/refresh"
    const val NOTICE_LIST: String = "/notice/all"
    const val NOTICE: String = "/notice/"
    const val SEARCH: String = "/lecture/search/"
    const val ID: String = "/user/find-id"
    const val PASSWORD: String = "/user/find-pw"
    const val PASSWORD_RESET: String = "/user/reset-pw"
    const val LOGIN: String = "/user/login"
    const val QUIT: String = "/user/quit"
    const val MY_PAGE: String = "/user/my-page"
    const val BAN_REASON: String = "/user/restricted-reason"
    const val BLACKLIST_REASON: String = "/user/blacklist-reason"
    const val EVALUATE_POST: String = "/evaluate-posts/written"
    const val DELETE_EVALUATE_POST: String = "/evaluate-posts/"
    const val EXAM_POSTS: String = "/exam-posts/written"
    const val DELETE_EXAM_POSTS: String = "/exam-posts/"
    const val PURCHASE_HISTORY: String = "/exam-posts/purchase"
    const val SIGN_UP: String = "/user/join"
    const val SIGN_UP_ID_CHECK: String = "/user/check-id"
    const val SIGN_UP_EMAIL_CHECK: String = "/user/check-email"
    const val LECTURE_MAIN: String = "/lecture/all/"
    const val LECTURE_DETAIL_INFO = "/lecture/"
    const val LECTURE_DETAIL_EVALUATION = "/evaluate-posts/"
    const val LECTURE_DETAIL_EXAM = "/exam-posts/"
    const val WRITE_LECTURE_EVALUATION = "/evaluate-posts/"
    const val WRITE_LECTURE_EXAM = "/exam-posts/"
    const val EDIT_LECTURE_EVALUATION = "/evaluate-posts/"
    const val EDIT_LECTURE_EXAM = "/exam-posts/"
    const val BUY_EXAM = "/exam-posts/purchase/"
    const val OPEN_MAJOR_VERSION = "/suwiki/version/"
    const val OPEN_MAJOR_LIST_UPDATE = "/suwiki/majorType"
    const val BOOKMARK = "/user/favorite-major/"
    const val REPORT_EVALUATION = "/user/report/evaluate"
    const val REPORT_EXAM = "/user/report/exam"
}

object WriteFragmentTitle {
    const val EDIT_MY_EVALUATION = "강의평가 수정"
    const val EDIT_MY_EXAM = "시험 정보 수정"
    const val WRITE_EVALUATION = "강의평가 작성"
    const val WRITE_EXAM = "시험정보 작성"
    const val FINISH_EXAM = "작성하기 +20P"
    const val FINISH_EVALUATION = "작성하기 +10P"
    const val FINISH_EDIT = "수정하기"
}

object UserPoint {
    const val WRITE_EVALUATION = 10
    const val WRITE_EXAM = 20
    const val REPORT = 1
    const val VIEW_EXAM = -20
    const val DELETE_POST = -30
}

object LectureItemViewType {
    const val SHORT = 0 // 강의평가 목록
    const val LOADING = 1 // 내가 쓴 강의평가
    const val USER_EXAM = 2 // 내가 쓴 시험정보
    const val LECTURE = 3 // 세부 강의평가 정보
    const val EXAM = 4 // 세부 시험정보
    const val FOOTER = -1 // 세부 시험정보 가림 (content 텍스트 색상 변경 및 report 버튼 클릭 방지
}

object PostData {
    val team = mapOf(0 to "없음", 1 to "있음")
    val difficulty = mapOf(0 to "학점느님", 1 to "보통", 2 to "까다로움")
    val homework = mapOf(0 to "없음", 1 to "보통", 2 to "많음")

    enum class PostDataType {
        TEAM, DIFFICULTY, HOMEWORK
    }
}

object TextLength {
    const val MIN_SEARCH_TEXT_LENGTH = 2
}

object LectureApiOption {
    const val MODIFIED = "modifiedDate"
    const val SATISFACTION = "lectureSatisfactionAvg"
    const val HONEY = "lectureHoneyAvg"
    const val LEARNING = "lectureLearningAvg"
    const val BEST = "lectureTotalAvg"
}

// 무한 스크롤
const val LAST_PAGE = 0

object TimetableCellColor {
    val colorMap = mapOf<String, Int>(
        "Pink" to Color.rgb(254, 136, 136), // 핑크
        "Orange" to Color.rgb(255, 193, 82), // 주황
        "Purple" to Color.rgb(204, 154, 243), // 보라
        "Sky" to Color.rgb(137, 200, 254), // 하늘
        "Green" to Color.rgb(165, 220, 129), // 연두
        "Brown" to Color.rgb(194, 143, 98), // 갈색
        "Gray" to Color.rgb(194, 193, 189), // 회색
        "Navy" to Color.rgb(67, 87, 150), // 남색
        "darkGreen" to Color.rgb(107, 143, 84), // 진녹색
        "lightBrown" to Color.rgb(255, 187, 128), // 연갈색
        "darkPurple" to Color.rgb(161, 121, 192), // 진보라색
        "darkGray" to Color.rgb(143, 142, 139) // 진회색
    )
}

object FragmentType {
    const val EVALUATION = 1
    const val SEARCH_RESULT = 2
    const val ADD_CLASS = 3
}

enum class TimeTableListClickType {
    ITEM_CLICK, EDIT_CLICK
}

object TimetableColor {
    const val BROWN = -4026526
    const val PURPLE = -3368205
    const val GRAY = -4013635
    const val SKY = -7747330
    const val ORANGE = -16046
    const val PINK = -96120
    const val NAVY = -12363882
    const val GREEN = -5907327
    const val APRICOT = -17536
    const val DARK_GREEN = -9728172
    const val DARK_PURPLE = -6194752
    const val MINT = -7369077
}

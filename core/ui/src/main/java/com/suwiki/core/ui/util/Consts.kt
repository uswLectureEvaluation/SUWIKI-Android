package com.suwiki.core.ui.util

const val SCHOOL_HOMEPAGE = "https://portal.suwon.ac.kr/enview/"
const val ASK_SITE = "https://alike-pump-ae3.notion.site/SUWIKI-2cd58468e90b404fbd3e30b8b2c0b699"
const val FEEDBACK_SITE = "https://forms.gle/tZByKoN6rJCysvNz6"
const val TERMS_SITE = "https://sites.google.com/view/suwiki-policy-terms/"
const val PRIVACY_POLICY_SITE = "https://sites.google.com/view/suwiki-policy-privacy"
const val PLAY_STORE_SITE = "https://play.google.com/store/apps/details?id=com.kunize.uswtimetable&hl=ko-KR"

object REGEX {
  val ID = """^[A-Za-z0-9]{6,20}$""".toRegex()
  val PASSWORD = """^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$""".toRegex()
  val EMAIL = """^[A-Za-z0-9._%+-]+@suwon\.ac\.kr$""".toRegex()
}

const val TEXT_FIELD_DEBOUNCE = 800L

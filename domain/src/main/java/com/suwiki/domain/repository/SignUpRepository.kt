package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface SignUpRepository {
  /**
   * @return 중복이면 true, 사용 가능하면 false 반환
   * */
  suspend fun checkId(id: String): Result<Boolean>

  /**
   * @return 중복이면 true, 사용 가능하면 false 반환
   * */
  suspend fun checkEmail(email: String): Result<Boolean>

  /**
   * @return 회원가입이 성공적으로 이루어졌으면 true, 실패하면 false 반환
   * */
  suspend fun signUp(id: String, pw: String, email: String): Result<Boolean>
}

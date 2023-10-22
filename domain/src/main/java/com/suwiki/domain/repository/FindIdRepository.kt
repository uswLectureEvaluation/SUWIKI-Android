package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface FindIdRepository {
  /**
   * @return 아이디 찾기 성공시 true, 실패시 false 반환
   * */
  suspend fun findId(email: String): Result<Boolean>
}

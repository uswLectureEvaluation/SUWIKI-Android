package com.suwiki.core.model.exception

class RequestFailException(
  override val message: String = "요청을 처리하지 못했어요.",
) : RuntimeException()

class ForbiddenException(
  override val message: String = "제한된 요청이에요.",
) : RuntimeException()

class NetworkException(
  override val message: String = "네트워크 연결 상태 또는 수위키 서버에 문제가 있어요.",
) : RuntimeException()

class UnknownException(
  override val message: String = "알 수 없는 에러가 발생했어요.",
) : RuntimeException()

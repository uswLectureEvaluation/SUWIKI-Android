package com.suwiki.core.model.exception

enum class SuwikiServerError(val exception: Throwable) {
  USER003(IncorrectEmailFormException()),
  USER004(UserNotExistException()),
  USER005(PasswordErrorException()),
  USER006(UserPointLackException()),
  USER007(NeedLoginException()),
  USER008(RestrictedUserException()),
  USER009(BlackListUserException()),
  USER010(LoginIdOrEmailOverlapException()),
  USER011(PasswordNotChangedException()),
  USER013(UserNotFoundByEmailException()),
  USER014(UserAlreadyBlackedListedException()),
  USER015(UserIsBlackListedException()),
  USER016(UserNotFoundByLoginIdException()),

  CONFIRMATION_TOKEN001(EmailNotAuthedException()),
  CONFIRMATION_TOKEN002(EmailValidatedException()),
  CONFIRMATION_TOKEN003(EmailValidatedErrorRetryException()),
  CONFIRMATION_TOKEN004(EmailAuthTokenAlreadyUsedException()),

  POSTS001(PostWriteOverlapException()),

  EXAM_POST001(ExamPostNotFoundException()),
  EXAM_POST002(ExamPostAlreadyPurchaseException()),

  EVALUATE_POST001(EvaluatePostNotFoundException()),

  TOKEN001(TokenExpiredException()),
  TOKEN002(TokenIsBrokenException()),

  SECURITY001(UnAuthenticatedException()),
  SECURITY003(LoginFailedException()),

  MAIL001(SendMailFailedException())
}

class IncorrectEmailFormException(
  override val message: String = "이메일 형식이 잘못되었어요.",
) : RuntimeException()

class UserNotExistException(
  override val message: String = "존재하지 않는 사용자에요.",
) : RuntimeException()

class PasswordErrorException(
  override val message: String = "비밀번호를 확인해주세요.",
) : RuntimeException()

class UserPointLackException(
  override val message: String = "포인트가 부족해요.",
) : RuntimeException()

class NeedLoginException(
  override val message: String = "로그인이 필요해요.",
) : RuntimeException()

class RestrictedUserException(
  override val message: String = "접근 권한이 없어요.",
) : RuntimeException()

class BlackListUserException(
  override val message: String = "블랙리스트 대상이에요. 이용할 수 없어요.",
) : RuntimeException()

class LoginIdOrEmailOverlapException(
  override val message: String = "이미 사용중인 아이디 혹은 이메일이에요.",
) : RuntimeException()

class PasswordNotChangedException(
  override val message: String = "이전 비밀번호와 동일하게 변경할 수 없어요.",
) : RuntimeException()

class EmailNotAuthedException(
  override val message: String = "이메일 인증이 완료되지 않았어요. 교내 포털 사이트에서 이메일 인증을 완료해주세요.",
) : RuntimeException()

class EmailValidatedException(
  override val message: String = "이메일 인증에 실패했습니다.",
) : RuntimeException()

class EmailValidatedErrorRetryException(
  override val message: String = "이메일 인증 만료기간이 지나거나, 예기치 못한 오류로 이메일 인증에 실패했습니다. 회원가입을 다시 진행해주세요",
) : RuntimeException()

class EmailAuthTokenAlreadyUsedException(
  override val message: String = "이미 사용된 인증 토큰 입니다.",
) : RuntimeException()

class PostWriteOverlapException(
  override val message: String = "이미 작성한 정보입니다.",
) : RuntimeException()

class ExamPostNotFoundException(
  override val message: String = "해당 시험정보를 찾을 수 없습니다.",
) : RuntimeException()

class ExamPostAlreadyPurchaseException(
  override val message: String = "이미 구매한 시험정보 입니다.",
) : RuntimeException()

class EvaluatePostNotFoundException(
  override val message: String = "해당 강의평가를 찾을 수 없습니다.",
) : RuntimeException()

class UserAlreadyBlackedListedException(
  override val message: String = "이미 블랙리스트인 사용자 입니다.",
) : RuntimeException()

class UserIsBlackListedException(
  override val message: String = "신고 당한 횟수 3회 누적으로 블랙리스트 조치 되었습니다. 더 이상 서비스를 이용할 수 없습니다.",
) : RuntimeException()

class UserNotFoundByEmailException(
  override val message: String = "해당 이메일에 대한 유저를 찾을 수 없습니다.",
) : RuntimeException()

class UserNotFoundByLoginIdException(
  override val message: String = "해당 아이디에 대한 유저를 찾을 수 없습니다.",
) : RuntimeException()

class TokenExpiredException(
  override val message: String = "토큰이 만료되었습니다 다시 로그인 해주세요",
) : RuntimeException()

class TokenIsBrokenException(
  override val message: String = "토큰이 유효하지 않습니다.",
) : RuntimeException()

class UnAuthenticatedException(
  override val message: String = "로그인이 필요해요.",
) : RuntimeException()

class LoginFailedException(
  override val message: String = "아이디, 비밀번호를 확인해주세요.",
) : RuntimeException()

class SendMailFailedException(
  override val message: String = "메일 전송에 실패했어요.",
) : RuntimeException()

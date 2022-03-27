package com.kunize.uswtimetable.ui.repository.signup

class SignUpRepository(private val dataSource: SignUpRemoteDataSource) {
        suspend fun checkId(id: String) = dataSource.checkId(id)
    /*suspend fun checkId(id: String): Result<OverlapCheckDto> {
        val result: Result<OverlapCheckDto>? = null
        withContext(Dispatchers.IO) {
            val checkIdResult = dataSource.checkId(id)
            result = return@withContext if (checkIdResult.isSuccessful) {
                Result.Success(checkIdResult.body()!!)
            } else {
                Result.Error(IOException(checkIdResult.message()))
            }
        }
        return result!!
    }*/

    suspend fun checkEmail(email: String) = dataSource.checkEmail(email)
    suspend fun signUp(id: String, pw: String, email: String) = dataSource.signup(id, pw, email)
}
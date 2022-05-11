package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.UserIdEmail
import com.kunize.uswtimetable.retrofit.IRetrofit

class FindPwRepository {

    suspend fun findPw(id: String, email: String) = IRetrofit.getInstanceWithNoToken().findPassword(UserIdEmail(id, email))
}

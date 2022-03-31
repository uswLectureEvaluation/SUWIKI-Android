package com.kunize.uswtimetable.ui.repository.find_user_info

import com.kunize.uswtimetable.dataclass.UserIdEmail
import com.kunize.uswtimetable.retrofit.IRetrofit

class FindPwRepository {

    suspend fun findPw(id: String, email: String) = IRetrofit.getInstanceWithNoToken().findPassword(UserIdEmail(id, email))
}

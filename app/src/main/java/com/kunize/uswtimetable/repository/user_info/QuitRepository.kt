package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.retrofit.IRetrofit

class QuitRepository {

    suspend fun quit(id: String, password: String) =
        IRetrofit.getInstance().quit(LoginIdPassword(
            loginId = id,
            password = password
        ))
}

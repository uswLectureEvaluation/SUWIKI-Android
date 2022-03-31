package com.kunize.uswtimetable.ui.repository.user_info

import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.retrofit.IRetrofit

class QuitRepository {

    suspend fun quit(id: String, password: String) =
        IRetrofit.getInstanceWithNoToken().quit(LoginIdPassword(
            loginId = id,
            password = password
        ))
}

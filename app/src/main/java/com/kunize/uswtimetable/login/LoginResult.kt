package com.kunize.uswtimetable.login

import com.kunize.uswtimetable.dataclass.LoggedInUserView

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
package com.kunize.uswtimetable.repository.user_info

import com.kunize.uswtimetable.dataclass.EmailDto
import com.kunize.uswtimetable.retrofit.IRetrofit

class FindIdRepository {

    suspend fun findId(email: String) =
        IRetrofit.getInstanceWithNoToken().findId(EmailDto(email))
}

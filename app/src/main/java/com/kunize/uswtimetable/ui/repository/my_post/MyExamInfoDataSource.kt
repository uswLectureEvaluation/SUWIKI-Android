package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyExamInfoListDto
import retrofit2.Response

interface MyExamInfoDataSource {
    suspend fun getMyExamInfoData(page: Int): Response<MyExamInfoListDto>
}
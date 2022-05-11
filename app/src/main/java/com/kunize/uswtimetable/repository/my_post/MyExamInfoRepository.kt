package com.kunize.uswtimetable.repository.my_post

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyExamInfoRepository(private val myExamInfoDataSource: MyExamInfoRemoteDataSource) {
    suspend fun getExamInfos(page: Int) = withContext(Dispatchers.IO) {
        myExamInfoDataSource.getMyExamInfoData(page)
    }
}
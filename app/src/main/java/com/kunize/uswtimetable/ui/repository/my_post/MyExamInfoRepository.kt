package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyExamInfo

class MyExamInfoRepository(private val myExamInfoDataSource: MyExamInfoDataSource) {
    fun getExamInfos(): List<MyExamInfo> {
        return myExamInfoDataSource.getMyExamInfoData()
    }
}
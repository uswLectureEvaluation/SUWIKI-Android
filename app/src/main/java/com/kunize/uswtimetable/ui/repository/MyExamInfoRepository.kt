package com.kunize.uswtimetable.ui.repository

import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.dataclass.MyExamInfo
import com.kunize.uswtimetable.ui.more.MyExamInfoDataSource
import com.kunize.uswtimetable.ui.more.MyPostDataSource

class MyExamInfoRepository(private val myExamInfoDataSource: MyExamInfoDataSource) {
    fun getExamInfos(): List<MyExamInfo> {
        return myExamInfoDataSource.getMyExamInfoData()
    }
}
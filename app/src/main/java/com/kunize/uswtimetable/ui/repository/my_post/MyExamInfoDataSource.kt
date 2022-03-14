package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyExamInfo

interface MyExamInfoDataSource {
    fun getMyExamInfoData(): List<MyExamInfo>
}
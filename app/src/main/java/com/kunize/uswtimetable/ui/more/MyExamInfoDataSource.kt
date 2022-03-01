package com.kunize.uswtimetable.ui.more

import com.kunize.uswtimetable.dataclass.MyExamInfo

interface MyExamInfoDataSource {
    fun getMyExamInfoData(): List<MyExamInfo>
}
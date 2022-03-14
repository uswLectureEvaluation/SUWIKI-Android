package com.kunize.uswtimetable.ui.repository.my_post

import com.kunize.uswtimetable.dataclass.MyEvaluation

class MyPostRepository(private val myPostDataSource: MyPostDataSource) {
    fun getEvaluations(): List<MyEvaluation> {
        return myPostDataSource.getMyPostData()
    }
}
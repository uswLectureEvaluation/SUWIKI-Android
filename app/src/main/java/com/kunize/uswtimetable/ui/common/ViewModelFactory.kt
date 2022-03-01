package com.kunize.uswtimetable.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.signup.SignUpViewModel
import com.kunize.uswtimetable.ui.more.MyExamInfoAssetDataSource
import com.kunize.uswtimetable.ui.more.MyExamInfoViewModel
import com.kunize.uswtimetable.ui.more.MyPostAssetDataSource
import com.kunize.uswtimetable.ui.more.MyPostViewModel
import com.kunize.uswtimetable.ui.repository.MyExamInfoRepository
import com.kunize.uswtimetable.ui.repository.MyPostRepository
import com.kunize.uswtimetable.util.AssetLoader
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MyPostViewModel::class.java) -> {
                val repository = MyPostRepository(MyPostAssetDataSource(AssetLoader(context)))
                MyPostViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel() as T
            }
            modelClass.isAssignableFrom(MyExamInfoViewModel::class.java) -> {
                val repository = MyExamInfoRepository(MyExamInfoAssetDataSource(AssetLoader(context)))
                MyExamInfoViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        }
    }
}
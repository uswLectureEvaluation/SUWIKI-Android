package com.kunize.uswtimetable.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.signup.SignUpViewModel
import com.kunize.uswtimetable.ui.more.MyPostAssetDataSource
import com.kunize.uswtimetable.ui.more.MyPostViewModel
import com.kunize.uswtimetable.ui.repository.MyPostRepository
import com.kunize.uswtimetable.util.AssetLoader
import java.lang.IllegalArgumentException

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPostViewModel::class.java)) {
            val repository = MyPostRepository(MyPostAssetDataSource(AssetLoader(context)))
            return MyPostViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel() as T
        }

        else {
            throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
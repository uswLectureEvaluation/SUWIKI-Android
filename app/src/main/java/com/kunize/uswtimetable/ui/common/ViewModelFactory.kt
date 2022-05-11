package com.kunize.uswtimetable.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel
import com.kunize.uswtimetable.ui.lecture_info.LectureInfoViewModel
import com.kunize.uswtimetable.ui.login.LoginViewModel
import com.kunize.uswtimetable.ui.mypage.MyEvaluationViewModel
import com.kunize.uswtimetable.ui.mypage.MyExamInfoViewModel
import com.kunize.uswtimetable.ui.mypage.MyPageViewModel
import com.kunize.uswtimetable.ui.mypage.PurchaseHistoryViewModel
import com.kunize.uswtimetable.ui.notice.NoticeDetailViewModel
import com.kunize.uswtimetable.ui.notice.NoticeViewModel
import com.kunize.uswtimetable.repository.evaluation.EvaluationRemoteDataSource
import com.kunize.uswtimetable.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.repository.lecture_info.LectureInfoRemoteDataSource
import com.kunize.uswtimetable.repository.lecture_info.LectureInfoRepository
import com.kunize.uswtimetable.repository.login.LoginRemoteDataSource
import com.kunize.uswtimetable.repository.login.LoginRepository
import com.kunize.uswtimetable.repository.my_post.MyPostRemoteDataSource
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.repository.notice.NoticeDetailRemoteDataSource
import com.kunize.uswtimetable.repository.notice.NoticeDetailRepository
import com.kunize.uswtimetable.repository.notice.NoticeRemoteDataSource
import com.kunize.uswtimetable.repository.notice.NoticeRepository
import com.kunize.uswtimetable.repository.search_result.SearchResultRemoteDataSource
import com.kunize.uswtimetable.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.repository.signup.SignUpRemoteDataSource
import com.kunize.uswtimetable.repository.signup.SignUpRepository
import com.kunize.uswtimetable.repository.user_info.FindIdRepository
import com.kunize.uswtimetable.repository.user_info.FindPwRepository
import com.kunize.uswtimetable.repository.user_info.QuitRepository
import com.kunize.uswtimetable.repository.user_info.ResetPasswordRepository
import com.kunize.uswtimetable.repository.write.WriteRemoteDataSource
import com.kunize.uswtimetable.repository.write.WriteRepository
import com.kunize.uswtimetable.ui.search_result.SearchResultViewModel
import com.kunize.uswtimetable.ui.signup.SignUpViewModel
import com.kunize.uswtimetable.ui.user_info.FindIdViewModel
import com.kunize.uswtimetable.ui.user_info.FindPwViewModel
import com.kunize.uswtimetable.ui.user_info.QuitViewModel
import com.kunize.uswtimetable.ui.user_info.ResetPasswordViewModel
import com.kunize.uswtimetable.ui.write.WriteViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // SignUp
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                val apiService = IRetrofit.getInstanceWithNoToken()
                val repository = SignUpRepository(SignUpRemoteDataSource(apiService))
                SignUpViewModel(repository) as T
            }
            // User Info
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = LoginRepository(LoginRemoteDataSource(apiService))
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FindIdViewModel::class.java) -> {
                val repository = FindIdRepository()
                FindIdViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FindPwViewModel::class.java) -> {
                val repository = FindPwRepository()
                FindPwViewModel(repository) as T
            }
            modelClass.isAssignableFrom(QuitViewModel::class.java) -> {
                val repository = QuitRepository()
                QuitViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) -> {
                val repository = ResetPasswordRepository()
                ResetPasswordViewModel(repository) as T
            }
            // MyPage
            modelClass.isAssignableFrom(MyPageViewModel::class.java) -> {
                MyPageViewModel() as T
            }
            modelClass.isAssignableFrom(MyEvaluationViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = MyPostRepository(MyPostRemoteDataSource(apiService))
                MyEvaluationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MyExamInfoViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = MyPostRepository(MyPostRemoteDataSource(apiService))
                MyExamInfoViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PurchaseHistoryViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = MyPostRepository(MyPostRemoteDataSource(apiService))
                PurchaseHistoryViewModel(repository) as T
            }
            // Notice
            modelClass.isAssignableFrom(NoticeViewModel::class.java) -> {
                val repository = NoticeRepository(NoticeRemoteDataSource())
                NoticeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NoticeDetailViewModel::class.java) -> {
                val repository = NoticeDetailRepository(NoticeDetailRemoteDataSource())
                NoticeDetailViewModel(repository) as T
            }
            // ...
            modelClass.isAssignableFrom(EvaluationViewModel::class.java) -> {
                val apiService = IRetrofit.getInstanceWithNoToken()
                val repository = EvaluationRepository(EvaluationRemoteDataSource(apiService))
                EvaluationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchResultViewModel::class.java) -> {
                val apiService = IRetrofit.getInstanceWithNoToken()
                val repository = SearchResultRepository(SearchResultRemoteDataSource(apiService))
                SearchResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LectureInfoViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = LectureInfoRepository(LectureInfoRemoteDataSource(apiService))
                LectureInfoViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WriteViewModel::class.java) -> {
                val apiService = IRetrofit.getInstance()
                val repository = WriteRepository(WriteRemoteDataSource(apiService))
                WriteViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        }
    }
}
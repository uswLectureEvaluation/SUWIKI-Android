package com.kunize.uswtimetable.data.module

import com.kunize.uswtimetable.data.datasource.LoginDataSource
import com.kunize.uswtimetable.data.datastore.UserPreference
import com.kunize.uswtimetable.data.repository.AuthRepositoryImpl
import com.kunize.uswtimetable.data.repository.LoginRepositoryImpl
import com.kunize.uswtimetable.data.repository.LogoutRepositoryImpl
import com.kunize.uswtimetable.data.repository.UserRepositoryImpl
import com.kunize.uswtimetable.domain.di.AuthApiService
import com.kunize.uswtimetable.domain.di.IoDispatcher
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.domain.repository.AuthRepository
import com.kunize.uswtimetable.domain.repository.LoginRepository
import com.kunize.uswtimetable.domain.repository.LogoutRepository
import com.kunize.uswtimetable.domain.repository.UserRepository
import com.kunize.uswtimetable.repository.evaluation.EvaluationDataSource
import com.kunize.uswtimetable.repository.evaluation.EvaluationRemoteDataSource
import com.kunize.uswtimetable.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.repository.my_post.MyPostDataSource
import com.kunize.uswtimetable.repository.my_post.MyPostRemoteDataSource
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.repository.notice.NoticeDetailDataSource
import com.kunize.uswtimetable.repository.notice.NoticeDetailRemoteDataSource
import com.kunize.uswtimetable.repository.notice.NoticeDetailRepository
import com.kunize.uswtimetable.repository.notice.NoticeRemoteDataSource
import com.kunize.uswtimetable.repository.notice.NoticeRepository
import com.kunize.uswtimetable.repository.open_major.OpenMajorDataSource
import com.kunize.uswtimetable.repository.open_major.OpenMajorRemoteDataSource
import com.kunize.uswtimetable.repository.open_major.OpenMajorRepository
import com.kunize.uswtimetable.repository.search_result.SearchResultDataSource
import com.kunize.uswtimetable.repository.search_result.SearchResultRemoteDataSource
import com.kunize.uswtimetable.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.repository.signup.SignUpDataSource
import com.kunize.uswtimetable.repository.signup.SignUpRemoteDataSource
import com.kunize.uswtimetable.repository.signup.SignUpRepository
import com.kunize.uswtimetable.repository.user_info.FindIdRepository
import com.kunize.uswtimetable.repository.user_info.FindPwRepository
import com.kunize.uswtimetable.repository.user_info.QuitRepository
import com.kunize.uswtimetable.repository.user_info.ResetPasswordRepository
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.mypage.suspend_history.SuspensionHistoryDataSource
import com.kunize.uswtimetable.ui.mypage.suspend_history.SuspensionHistoryRemoteDataSource
import com.kunize.uswtimetable.ui.mypage.suspend_history.SuspensionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideLoginDataSource(
        @OtherApiService apiService: IRetrofit,
    ): LoginDataSource {
        return LoginDataSource(apiService)
    }

    @Provides
    fun provideLoginRepository(
        datasource: LoginDataSource,
    ): LoginRepository {
        return LoginRepositoryImpl(datasource)
    }

    @Provides
    fun provideLogoutRepository(
        userPreference: UserPreference,
    ): LogoutRepository {
        return LogoutRepositoryImpl(userPreference)
    }

    @Provides
    fun provideUserRepository(
        @AuthApiService apiService: IRetrofit,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userPreference: UserPreference,
    ): UserRepository {
        return UserRepositoryImpl(
            apiService,
            ioDispatcher,
            userPreference,
        )
    }

    @Provides
    fun provideAuthRepository(
        @OtherApiService apiService: IRetrofit,
    ): AuthRepository {
        return AuthRepositoryImpl(
            apiService,
        )
    }

    @Provides
    fun provideOpenMajorDataSource(@OtherApiService apiService: IRetrofit): OpenMajorDataSource {
        return OpenMajorRemoteDataSource(apiService)
    }

    @Provides
    fun provideOpenMajorRepository(dataSource: OpenMajorDataSource): OpenMajorRepository {
        return OpenMajorRepository(dataSource)
    }

    @Provides
    fun provideMyPostDataSource(
        @AuthApiService apiService: IRetrofit,
    ): MyPostDataSource {
        return MyPostRemoteDataSource(apiService)
    }

    @Provides
    fun provideMyPostRepository(
        dataSource: MyPostDataSource,
    ): MyPostRepository {
        return MyPostRepository(dataSource)
    }

    @Provides
    fun provideEvaluationRemoteDataSource(
        @AuthApiService apiService: IRetrofit,
    ): EvaluationDataSource {
        return EvaluationRemoteDataSource(apiService)
    }

    @Provides
    fun provideEvaluationRepository(
        evaluationDataSource: EvaluationDataSource,
    ): EvaluationRepository {
        return EvaluationRepository(evaluationDataSource)
    }

    @Provides
    fun provideSearchResultDataSource(
        @OtherApiService apiService: IRetrofit,
    ): SearchResultDataSource {
        return SearchResultRemoteDataSource(apiService)
    }

    @Provides
    fun provideSearchResultRepository(
        dataSource: SearchResultDataSource,
    ): SearchResultRepository {
        return SearchResultRepository(dataSource)
    }

    @Provides
    fun provideSuspensionHistoryDataSource(
        @AuthApiService apiService: IRetrofit,
    ): SuspensionHistoryDataSource {
        return SuspensionHistoryRemoteDataSource(apiService)
    }

    @Provides
    fun provideSuspensionRepository(
        dataSource: SuspensionHistoryDataSource,
    ): SuspensionRepository {
        return SuspensionRepository(dataSource)
    }

    @Provides
    fun provideNoticeDataSource(
        @OtherApiService apiService: IRetrofit,
    ): NoticeRemoteDataSource {
        return NoticeRemoteDataSource(apiService)
    }

    @Provides
    fun provideNoticeRepository(
        dataSource: NoticeRemoteDataSource,
    ): NoticeRepository {
        return NoticeRepository(dataSource)
    }

    @Provides
    fun provideNoticeDetailDataSource(
        @OtherApiService apiService: IRetrofit,
    ): NoticeDetailDataSource {
        return NoticeDetailRemoteDataSource(apiService)
    }

    @Provides
    fun provideNoticeDetailRepository(
        noticeDetailRepository: NoticeDetailDataSource,
    ): NoticeDetailRepository {
        return NoticeDetailRepository(noticeDetailRepository)
    }

    @Provides
    fun provideResetPasswordRepository(
        @AuthApiService apiService: IRetrofit,
    ): ResetPasswordRepository {
        return ResetPasswordRepository(apiService)
    }

    @Provides
    fun provideQuitRepository(
        @AuthApiService apiService: IRetrofit,
    ): QuitRepository {
        return QuitRepository(apiService)
    }

    @Provides
    fun provideFindIdRepository(
        @OtherApiService apiService: IRetrofit,
    ): FindIdRepository {
        return FindIdRepository(apiService)
    }

    @Provides
    fun provideFindPwRepository(
        @OtherApiService apiService: IRetrofit,
    ): FindPwRepository {
        return FindPwRepository(apiService)
    }

    @Provides
    fun provideSignupDataSource(
        @OtherApiService apiService: IRetrofit,
    ): SignUpDataSource {
        return SignUpRemoteDataSource(apiService)
    }

    @Provides
    fun provideSignupRepository(
        dataSource: SignUpDataSource,
    ): SignUpRepository {
        return SignUpRepository(dataSource)
    }
}

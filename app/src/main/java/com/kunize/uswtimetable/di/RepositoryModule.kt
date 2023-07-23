package com.kunize.uswtimetable.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.suwiki.data.datasource.EvaluationDataSource
import com.suwiki.data.datasource.EvaluationRemoteDataSourceImpl
import com.suwiki.data.datasource.LoginDataSource
import com.suwiki.data.datasource.MyPostDataSource
import com.suwiki.data.datasource.MyPostRemoteDataSource
import com.suwiki.data.datasource.NoticeDataSource
import com.suwiki.data.datasource.NoticeDetailDataSource
import com.suwiki.data.datasource.NoticeDetailRemoteDataSource
import com.suwiki.data.datasource.OpenMajorDataSource
import com.suwiki.data.datasource.OpenMajorRemoteDataSource
import com.suwiki.data.datasource.SearchResultDataSource
import com.suwiki.data.datasource.SearchResultRemoteDataSource
import com.suwiki.data.datasource.SuspensionHistoryDataSource
import com.suwiki.data.datasource.SuspensionHistoryRemoteDataSource
import com.suwiki.data.db.OpenMajorDatabase
import com.suwiki.data.db.TimetableDatabase
import com.suwiki.data.model.UserPreference
import com.suwiki.data.network.ApiService
import com.suwiki.data.repository.AuthRepositoryImpl
import com.suwiki.data.repository.EvaluationRepositoryImpl
import com.suwiki.data.repository.FindIdRepositoryImpl
import com.suwiki.data.repository.FindPwRepositoryImpl
import com.suwiki.data.repository.LoginRepositoryImpl
import com.suwiki.data.repository.LogoutRepositoryImpl
import com.suwiki.data.repository.MyPostRepositoryImpl
import com.suwiki.data.repository.NoticeDetailRepositoryImpl
import com.suwiki.data.repository.NoticeRepositoryImpl
import com.suwiki.data.repository.OpenMajorRepositoryImpl
import com.suwiki.data.repository.QuitRepositoryImpl
import com.suwiki.data.repository.ResetPasswordRepositoryImpl
import com.suwiki.data.repository.SearchResultRepositoryImpl
import com.suwiki.data.repository.SettingRepositoryImpl
import com.suwiki.data.repository.SignUpRepositoryImpl
import com.suwiki.data.repository.SuspensionRepositoryImpl
import com.suwiki.data.repository.TimetableRepositoryImpl
import com.suwiki.data.repository.UserRepositoryImpl
import com.suwiki.data.repository.VersionRepositoryImpl
import com.suwiki.domain.di.AuthApiService
import com.suwiki.domain.di.IoDispatcher
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.repository.AuthRepository
import com.suwiki.domain.repository.EvaluationRepository
import com.suwiki.domain.repository.FindIdRepository
import com.suwiki.domain.repository.FindPwRepository
import com.suwiki.domain.repository.LoginRepository
import com.suwiki.domain.repository.LogoutRepository
import com.suwiki.domain.repository.MyPostRepository
import com.suwiki.domain.repository.NoticeDetailRepository
import com.suwiki.domain.repository.NoticeRepository
import com.suwiki.domain.repository.OpenMajorRepository
import com.suwiki.domain.repository.QuitRepository
import com.suwiki.domain.repository.ResetPasswordRepository
import com.suwiki.domain.repository.SearchResultRepository
import com.suwiki.domain.repository.SettingRepository
import com.suwiki.domain.repository.SignUpRepository
import com.suwiki.domain.repository.SuspensionRepository
import com.suwiki.domain.repository.TimetableRepository
import com.suwiki.domain.repository.UserRepository
import com.suwiki.domain.repository.VersionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideLoginDataSource(
        @OtherApiService apiService: ApiService,
        authRepository: AuthRepository,
    ): LoginDataSource {
        return LoginDataSource(apiService, authRepository)
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
        authRepository: AuthRepository,
    ): LogoutRepository {
        return LogoutRepositoryImpl(userPreference, authRepository)
    }

    @Provides
    fun provideUserRepository(
        @AuthApiService apiService: ApiService,
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
        @ApplicationContext context: Context,
        @OtherApiService apiService: ApiService,
    ): AuthRepository {
        return AuthRepositoryImpl(
            context,
            apiService,
        )
    }

    @Provides
    fun provideOpenMajorDataSource(
        @AuthApiService apiService: ApiService,
        database: OpenMajorDatabase,
    ): OpenMajorDataSource {
        return OpenMajorRemoteDataSource(apiService, database)
    }

    @Provides
    fun provideOpenMajorRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        dataSource: OpenMajorDataSource,
    ): OpenMajorRepository {
        return OpenMajorRepositoryImpl(ioDispatcher, dataSource)
    }

    @Provides
    fun provideMyPostDataSource(
        @AuthApiService apiService: ApiService,
    ): MyPostDataSource {
        return MyPostRemoteDataSource(apiService)
    }

    @Provides
    fun provideMyPostRepository(
        dataSource: MyPostDataSource,
    ): MyPostRepository {
        return MyPostRepositoryImpl(dataSource)
    }

    @Provides
    fun provideEvaluationRemoteDataSource(
        @AuthApiService apiService: ApiService,
    ): EvaluationDataSource {
        return EvaluationRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideEvaluationRepository(
        evaluationDataSource: EvaluationDataSource,
    ): EvaluationRepository {
        return EvaluationRepositoryImpl(evaluationDataSource)
    }

    @Provides
    fun provideSearchResultDataSource(
        @OtherApiService apiService: ApiService,
    ): SearchResultDataSource {
        return SearchResultRemoteDataSource(apiService)
    }

    @Provides
    fun provideSearchResultRepository(
        dataSource: SearchResultDataSource,
    ): SearchResultRepository {
        return SearchResultRepositoryImpl(dataSource)
    }

    @Provides
    fun provideSuspensionHistoryDataSource(
        @AuthApiService apiService: ApiService,
    ): SuspensionHistoryDataSource {
        return SuspensionHistoryRemoteDataSource(apiService)
    }

    @Provides
    fun provideSuspensionRepository(
        dataSource: SuspensionHistoryDataSource,
    ): SuspensionRepository {
        return SuspensionRepositoryImpl(dataSource)
    }

    @Provides
    fun provideNoticeDataSource(
        @OtherApiService apiService: ApiService,
    ): NoticeDataSource {
        return NoticeDataSource(apiService)
    }

    @Provides
    fun provideNoticeRepository(
        dataSource: NoticeDataSource,
    ): NoticeRepository {
        return NoticeRepositoryImpl(dataSource)
    }

    @Provides
    fun provideNoticeDetailDataSource(
        @OtherApiService apiService: ApiService,
    ): NoticeDetailDataSource {
        return NoticeDetailRemoteDataSource(apiService)
    }

    @Provides
    fun provideNoticeDetailRepository(
        noticeDetailRepository: NoticeDetailDataSource,
    ): NoticeDetailRepository {
        return NoticeDetailRepositoryImpl(noticeDetailRepository)
    }

    @Provides
    fun provideResetPasswordRepository(
        @AuthApiService apiService: ApiService,
    ): ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(apiService)
    }

    @Provides
    fun provideQuitRepository(
        @AuthApiService apiService: ApiService,
    ): QuitRepository {
        return QuitRepositoryImpl(apiService)
    }

    @Provides
    fun provideFindIdRepository(
        @OtherApiService apiService: ApiService,
    ): FindIdRepository {
        return FindIdRepositoryImpl(apiService)
    }

    @Provides
    fun provideFindPwRepository(
        @OtherApiService apiService: ApiService,
    ): FindPwRepository {
        return FindPwRepositoryImpl(apiService)
    }

    @Provides
    fun provideSignupRepository(
        @OtherApiService apiService: ApiService,
    ): SignUpRepository {
        return SignUpRepositoryImpl(apiService)
    }

    @Provides
    fun provideVersionRepository(
        @ApplicationContext context: Context,
        firebaseDatabase: FirebaseDatabase,
    ): VersionRepository {
        return VersionRepositoryImpl(context, firebaseDatabase)
    }

    @Provides
    fun provideTimetableRepository(
        firebaseDatabase: FirebaseDatabase,
        timetableDatabase: TimetableDatabase,
    ): TimetableRepository {
        return TimetableRepositoryImpl(firebaseDatabase, timetableDatabase)
    }

    @Provides
    fun provideSettingRepository(
        @ApplicationContext context: Context,
    ): SettingRepository {
        return SettingRepositoryImpl(context)
    }
}

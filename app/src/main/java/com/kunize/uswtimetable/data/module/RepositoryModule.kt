package com.kunize.uswtimetable.data.module

import com.kunize.uswtimetable.data.datastore.UserPreference
import com.kunize.uswtimetable.data.repository.AuthRepositoryImpl
import com.kunize.uswtimetable.data.repository.UserRepositoryImpl
import com.kunize.uswtimetable.data.repository.UserRepositoryLogoutImpl
import com.kunize.uswtimetable.domain.di.AuthApiService
import com.kunize.uswtimetable.domain.di.IoDispatcher
import com.kunize.uswtimetable.domain.di.OtherApiService
import com.kunize.uswtimetable.domain.di.UserRepositoryAll
import com.kunize.uswtimetable.domain.di.UserRepositoryLogout
import com.kunize.uswtimetable.domain.repository.AuthRepository
import com.kunize.uswtimetable.domain.repository.UserRepository
import com.kunize.uswtimetable.repository.evaluation.EvaluationDataSource
import com.kunize.uswtimetable.repository.evaluation.EvaluationRemoteDataSource
import com.kunize.uswtimetable.repository.evaluation.EvaluationRepository
import com.kunize.uswtimetable.repository.login.LoginDataSource
import com.kunize.uswtimetable.repository.login.LoginRemoteDataSource
import com.kunize.uswtimetable.repository.login.LoginRepository
import com.kunize.uswtimetable.repository.my_post.MyPostDataSource
import com.kunize.uswtimetable.repository.my_post.MyPostRemoteDataSource
import com.kunize.uswtimetable.repository.my_post.MyPostRepository
import com.kunize.uswtimetable.repository.open_major.OpenMajorDataSource
import com.kunize.uswtimetable.repository.open_major.OpenMajorRemoteDataSource
import com.kunize.uswtimetable.repository.open_major.OpenMajorRepository
import com.kunize.uswtimetable.repository.search_result.SearchResultDataSource
import com.kunize.uswtimetable.repository.search_result.SearchResultRemoteDataSource
import com.kunize.uswtimetable.repository.search_result.SearchResultRepository
import com.kunize.uswtimetable.retrofit.IRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideLoginDataSource(@AuthApiService apiService: IRetrofit): LoginDataSource {
        return LoginRemoteDataSource(apiService)
    }

    @Provides
    fun provideLoginRepository(dataSource: LoginDataSource): LoginRepository {
        return LoginRepository(dataSource)
    }

    @UserRepositoryAll
    @Provides
    fun provideUserRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @AuthApiService apiService: IRetrofit,
        userPreference: UserPreference,
    ): UserRepository {
        return UserRepositoryImpl(
            ioDispatcher,
            apiService,
            userPreference,
        )
    }

    @UserRepositoryLogout
    @Provides
    fun provideUserRepositoryLogout(
        userPreference: UserPreference,
    ): UserRepository {
        return UserRepositoryLogoutImpl(
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
}

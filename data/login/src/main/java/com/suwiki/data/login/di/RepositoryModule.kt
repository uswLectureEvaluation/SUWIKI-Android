package com.suwiki.data.login.di

import com.suwiki.data.login.repository.LoginRepositoryImpl
import com.suwiki.domain.login.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Singleton
  @Binds
  abstract fun bindLoginRepository(
    loginRepositoryImpl: LoginRepositoryImpl,
  ): LoginRepository
}

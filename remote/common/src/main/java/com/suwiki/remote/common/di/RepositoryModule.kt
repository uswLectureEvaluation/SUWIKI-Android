package com.suwiki.remote.common.di

import com.suwiki.remote.common.repository.AuthRepository
import com.suwiki.remote.common.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

  @Singleton
  @Binds
  internal abstract fun bindAuthRepository(
    authRepositoryImpl: AuthRepositoryImpl,
  ): AuthRepository
}

package com.suwiki.data.user.di

import com.suwiki.data.user.repository.UserRepositoryImpl
import com.suwiki.domain.user.repository.UserRepository
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
  abstract fun bindUserRepository(
    userRepositoryImpl: UserRepositoryImpl,
  ): UserRepository
}

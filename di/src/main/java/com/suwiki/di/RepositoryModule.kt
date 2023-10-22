package com.suwiki.di

import com.suwiki.data.repository.UserRepositoryImpl
import com.suwiki.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindUserRepository(
    userRepositoryImpl: UserRepositoryImpl,
  ): UserRepository
}

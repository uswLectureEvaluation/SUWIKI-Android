package com.suwiki.data.signup.di

import com.suwiki.data.signup.repository.SignUpRepositoryImpl
import com.suwiki.domain.signup.repository.SignUpRepository
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
  abstract fun bindSignUpRepository(
    signUpRepositoryImpl: SignUpRepositoryImpl,
  ): SignUpRepository
}

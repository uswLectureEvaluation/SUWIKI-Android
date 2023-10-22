package com.suwiki.data.openmajor.di

import com.suwiki.data.openmajor.repository.OpenMajorRepositoryImpl
import com.suwiki.domain.openmajor.repository.OpenMajorRepository
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
  abstract fun bindOpenMajorRepository(
    openMajorRepositoryImpl: OpenMajorRepositoryImpl,
  ): OpenMajorRepository
}

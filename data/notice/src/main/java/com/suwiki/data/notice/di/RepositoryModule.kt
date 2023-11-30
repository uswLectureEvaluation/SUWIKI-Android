package com.suwiki.data.notice.di

import com.suwiki.data.notice.repository.NoticeRepositoryImpl
import com.suwiki.domain.notice.repository.NoticeRepository
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
  abstract fun bindNoticeRepository(
    noticeRepositoryImpl: NoticeRepositoryImpl,
  ): NoticeRepository
}

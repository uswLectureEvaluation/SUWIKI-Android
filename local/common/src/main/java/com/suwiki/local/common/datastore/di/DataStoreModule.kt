package com.suwiki.local.common.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.suwiki.local.common.UserPreference
import com.suwiki.local.common.datastore.proto.UserPreferenceSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @Provides
  @Singleton
  fun providesUserPreferencesDataStore(
    @ApplicationContext context: Context,
    userPreferenceSerializer: UserPreferenceSerializer,
  ): DataStore<UserPreference> =
    DataStoreFactory.create(
      serializer = userPreferenceSerializer,
    ) {
      context.dataStoreFile("user_preference.pb")
    }

  @Singleton
  @Provides
  @NormalDataStore
  fun provideDataStore(
    @ApplicationContext applicationContext: Context,
  ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    corruptionHandler = ReplaceFileCorruptionHandler(
      produceNewData = { emptyPreferences() },
    ),
    produceFile = { applicationContext.preferencesDataStoreFile("suwiki-preference") },
  )
}

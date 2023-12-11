package com.suwiki.core.security.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.suwiki.core.security.SecurityPreferences
import com.suwiki.core.security.generateSecurityPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import tech.thdev.useful.encrypted.data.store.preferences.security.generateUsefulSecurity
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @Singleton
  @Provides
  @SecureDataStore
  fun provideEncryptedDataStore(
    @ApplicationContext applicationContext: Context,
  ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
    corruptionHandler = ReplaceFileCorruptionHandler(
      produceNewData = { emptyPreferences() },
    ),
    produceFile = { applicationContext.preferencesDataStoreFile("security-preference") },
  )

  @Singleton
  @Provides
  fun provideSecurityPreference(
    @SecureDataStore dataStore: DataStore<Preferences>,
  ): SecurityPreferences = dataStore.generateSecurityPreferences(generateUsefulSecurity())
}

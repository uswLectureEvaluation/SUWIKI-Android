package com.suwiki.core.database.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.TypeConverter
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suwiki.core.database.database.OpenLectureDatabase
import com.suwiki.core.database.database.OpenMajorDatabase
import com.suwiki.core.database.database.TimetableDatabase
import com.suwiki.core.database.database.migration.TIMETABLE_MIGRATION_1_2
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.sql.Time
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideOpenMajorDatabase(
    @ApplicationContext context: Context,
  ): OpenMajorDatabase {
    return Room
      .databaseBuilder(
        context,
        OpenMajorDatabase::class.java,
        DatabaseName.OPEN_MAJOR,
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideOpenLectureDatabase(
    @ApplicationContext context: Context,
  ): OpenLectureDatabase {
    return Room
      .databaseBuilder(
        context,
        OpenLectureDatabase::class.java,
        DatabaseName.OPEN_LECTURE,
      )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun provideTimetableDatabase(
    @ApplicationContext context: Context,
  ): TimetableDatabase {
    return Room
      .databaseBuilder(
        context,
        TimetableDatabase::class.java,
        DatabaseName.TIMETABLE,
      )
      .addMigrations(TIMETABLE_MIGRATION_1_2)
      .fallbackToDestructiveMigration()
      .build()
  }
}



/**
 * 상수 이름과 실제 값이 일치하지 않는 이유는 이전 버전과의 호환을 위해 레거시 데이터베이스의 이름을 변경할 수 없었기 때문입니다.
 */
object DatabaseName {
  const val OPEN_MAJOR = "open-major-database"
  const val OPEN_LECTURE = "timetable-database"
  const val TIMETABLE = "timetable-list-database"
}

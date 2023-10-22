package com.suwiki.remote.timetable.viewer.datasource

import com.google.firebase.database.FirebaseDatabase
import com.suwiki.core.model.timetable.TimetableData
import com.suwiki.data.timetable.viewer.datasource.RemoteTimetableDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteTimetableDataSourceImpl @Inject constructor(
  private val firebaseDatabase: FirebaseDatabase,
) : RemoteTimetableDataSource {
  companion object {
    private const val FIELD_MAJOR = "estbDpmNm"
    private const val FIELD_GRADE = "trgtGrdeCd"
    private const val FIELD_CLASS_NUMBER = "subjtCd"
    private const val FIELD_CLASS_DIVIDE_NUMBER = "diclNo"
    private const val FIELD_CLASS_NAME = "subjtNm"
    private const val FIELD_CLASSIFICATION = "facDvnm"
    private const val FIELD_PROFESSOR = "reprPrfsEnoNm"
    private const val FIELD_TIME = "timtSmryCn"
    private const val FIELD_CREDIT = "point"

    private const val SUFFIX_GRADE = "학년"
    private const val DEFAULT = "None"

    private const val DATABASE_TIMETABLE = "uswTimetable"
    private const val DATABASE_TIMETABLE_VERSION = "version"
  }

  override suspend fun fetchRemoteTimetableVersion(): Long {
    val timetableVersionDatabase = firebaseDatabase.getReference(DATABASE_TIMETABLE_VERSION)

    var version = 0L

    timetableVersionDatabase.get().addOnSuccessListener {
      version = it.value.toString().toLong()
    }.await()

    return version
  }

  override suspend fun fetchRemoteTimetable(): List<TimetableData> {
    val timetableDatabase = firebaseDatabase.getReference(DATABASE_TIMETABLE)

    val timetables = mutableListOf<TimetableData>()

    timetableDatabase.get().addOnSuccessListener {
      it.children.forEachIndexed { i, snapshot ->
        val data = snapshot.value as HashMap<*, *>
        val timetableData = TimetableData(
          number = i.toLong() + 1,
          major = data[FIELD_MAJOR].toString(),
          grade = data[FIELD_GRADE].toString() + SUFFIX_GRADE,
          classNumber = data[FIELD_CLASS_NUMBER].toString(),
          classDivideNumber = data[FIELD_CLASS_DIVIDE_NUMBER].toString(),
          className = data[FIELD_CLASS_NAME].toString(),
          classification = data[FIELD_CLASSIFICATION].toString(),
          professor = data[FIELD_PROFESSOR]?.toString() ?: DEFAULT,
          time = data[FIELD_TIME]?.toString() ?: DEFAULT,
          credit = data[FIELD_CREDIT].toString(),
        )
        timetables.add(timetableData)
      }
    }.await()
    return timetables
  }
}

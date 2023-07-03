package com.suwiki.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.suwiki.data.db.TimetableDatabase
import com.suwiki.data.db.entity.converter.toDomain
import com.suwiki.data.db.entity.converter.toEntity
import com.suwiki.domain.model.TimetableData
import com.suwiki.domain.repository.TimetableRepository
import javax.inject.Inject

class TimetableRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    timetableDatabase: TimetableDatabase,
) : TimetableRepository {

    private val timetableRemoteDB = firebaseDatabase.getReference("uswTimetable")
    private val timetableLocalDB = timetableDatabase.timetableDao()

    override suspend fun loadRemoteTimetable(): List<TimetableData> {
        val timetables = mutableListOf<TimetableData>()

        timetableRemoteDB.get().addOnSuccessListener {
            it.children.forEachIndexed { i, snapshot ->
                val data = snapshot.value as HashMap<*, *>
                val timetableData = TimetableData(
                    number = i.toLong() + 1,
                    major = data["estbDpmNm"].toString(),
                    grade = data["trgtGrdeCd"].toString() + "학년",
                    classNumber = data["subjtCd"].toString(),
                    classDivideNumber = data["diclNo"].toString(),
                    className = data["subjtNm"].toString(),
                    classification = data["facDvnm"].toString(),
                    professor = data["reprPrfsEnoNm"]?.toString() ?: "None",
                    time = data["timtSmryCn"]?.toString() ?: "None",
                    credit = data["point"].toString(),
                )
                Log.d("arrayTest", "$timetableData")
                timetables.add(timetableData)
            }
        }
        return timetables
    }

    override suspend fun loadLocalTimetable(): List<TimetableData> {
        return timetableLocalDB.getAll().map { it.toDomain() }
    }

    override suspend fun insert(data: TimetableData) {
        timetableLocalDB.insert(data.toEntity())
    }

    override suspend fun deleteAllLocalTimetable() {
        timetableLocalDB.deleteAll()
    }

    override suspend fun deleteLocalTimetable(data: TimetableData) {
        timetableLocalDB.delete(data.toEntity())
    }

    override suspend fun updateLocalTimetable(data: TimetableData) {
        timetableLocalDB.update(data.toEntity())
    }
}

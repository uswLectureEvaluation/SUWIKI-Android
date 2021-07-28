package com.kunize.uswtimetable

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kunize.uswtimetable.model.TimeTableData
import com.kunize.uswtimetable.model.TimeTableDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //firebase 설정
        val database: FirebaseDatabase?
        val firebaseVersion: DatabaseReference?
        val firebaseTimetableData: DatabaseReference?

        //Preferences 설정
        val versionPreferences = getSharedPreferences("version", Context.MODE_PRIVATE)
        var version = versionPreferences.getString("version", "202107271830")

        //Room 설정
        val db = TimeTableDatabase.getInstance(applicationContext)

        //기타
        val localDataList = mutableListOf<TimeTableData>()
        var originData: String
        var done = false
        var update: Boolean? = null

        //intent 설정
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //액티비티 스택제거

        database = FirebaseDatabase.getInstance()
        firebaseVersion = database.getReference("version")

        firebaseVersion.get().addOnSuccessListener {
            update = it.value.toString().toLong() > version!!.toLong()
            version = it.value.toString()
            Log.d("firebase", "$update ${it.value.toString().toLong()} // ${version!!.toLong()}")
        }

        firebaseTimetableData = database.getReference("timetable")


        CoroutineScope(IO).launch {
            while (true) {
                if (update != null)
                    break
                delay(500L)
            } //update 값이 입력될 때 까지 무한 루프
            if (update == true) {
                Log.d("firebase", "업데이트 실행 $version")
                firebaseTimetableData.get().addOnSuccessListener {
                    originData = it.value.toString()
                    Log.d("firebase", "공백 제거 전" + originData)
                    originData = originData.replace("\t", "")
                    Log.d("firebase", "공백 제거 후" + originData)
                    val tempData = originData.split("^ ")
                    var i = 1L
                    for (data in tempData) {
                        val specificData = data.split("!")
                        val emptyData = TimeTableData()
                        Log.d("testinin", "$specificData")
                        try {
                            emptyData.number = specificData[0].toLong()
                        } catch (e: Exception) {
                            emptyData.number = i
                        }
                        emptyData.major = specificData[1]
                        emptyData.grade = specificData[2]
                        emptyData.classNumber = specificData[3]
                        emptyData.classDivideNumber = specificData[4]
                        emptyData.className = specificData[5]
                        emptyData.classification = specificData[6]
                        emptyData.professor = specificData[7]
                        emptyData.time = specificData[8]
                        i++
                        Log.d("arrayTest", "$emptyData")
                        localDataList.add(emptyData)
                    }
                    done = true
                    Log.d("firebase", "추가 완료")
                }

                db!!.timetableDao().deleteAll()

                while (true) {
                    if (done) {
                        for (i in localDataList) {
                            db.timetableDao().insert(i)
                        }
                        versionPreferences.edit(true) {
                            putString("version", version)
                        }
                        break
                    } //시간표를 업데이트 완료 전까지 무한 루프
                    delay(1000L)
                }
                startActivity(intent)
            } else {
                startActivity(intent)
            }
        }


    }
}
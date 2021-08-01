package com.kunize.uswtimetable

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.edit
import androidx.room.Update
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kunize.uswtimetable.dataclass.TimeTableData
import com.kunize.uswtimetable.dao_database.TimeTableDatabase
import com.kunize.uswtimetable.databinding.ActivityStartBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private lateinit var appUpdateManager: AppUpdateManager

    companion object {
        const val MY_REQUEST_CODE = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener {
            appUpdateInfo ->
            if(appUpdateInfo.updateAvailability() ==
                    UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    IMMEDIATE,
                    this,
                    MY_REQUEST_CODE
                )
            }
        }

        binding.showProgress.text = "시간표 DB 버전 확인 중"

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
            binding.showProgress.text = "시간표 DB 버전 확인 완료"
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
                withContext(Main){
                    binding.showProgress.text = "서버로부터 시간표 DB 불러오는 중"
                }
                firebaseTimetableData.get().addOnSuccessListener {
                    originData = it.value.toString()
                    Log.d("firebase", "공백 제거 전" + originData)
                    originData = originData.replace("\t", "")
                    Log.d("firebase", "공백 제거 후" + originData)
                    val tempData = originData.split("^ ")
                    var i = 1L
                    for (data in tempData) {
                        try {
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
                        }catch (e: Exception) {
                            Log.d("firebase","에러 발생 $data $e")
                        }
                    }
                    done = true
                    Log.d("firebase", "추가 완료")
                }

                db!!.timetableDao().deleteAll()

                while (true) {
                    if (done) {
                        withContext(Main){
                            binding.showProgress.text = "시간표 DB 저장 중"
                        }
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

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener {
                appUpdateInfo->
                if(appUpdateInfo.updateAvailability() ==
                        UpdateAvailability
                            .DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == MY_REQUEST_CODE) {
            if(resultCode != RESULT_OK) {
                MaterialAlertDialogBuilder(this)
                    .setPositiveButton("확인") {
                        _,_ ->
                    }
                    .setMessage("원활한 앱 사용을 위해 업데이트를 권장합니다.")
                    .show()
            }
        }
    }
}
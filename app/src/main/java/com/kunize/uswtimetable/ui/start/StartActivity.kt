package com.kunize.uswtimetable.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kunize.uswtimetable.ui.main.MainActivity
import com.kunize.uswtimetable.data.local.TimeTableDatabase
import com.kunize.uswtimetable.databinding.ActivityStartBinding
import com.kunize.uswtimetable.data.local.TimeTableData
import com.kunize.uswtimetable.ui.login.LoginActivity.Companion.REMEMBER_LOGIN
import com.kunize.uswtimetable.ui.user_info.User
import com.kunize.uswtimetable.util.PreferenceManager
import com.kunize.uswtimetable.util.TimeTableSelPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    var toUpdate = false

    companion object {
        const val MY_REQUEST_CODE = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.showProgress.text = "시간표 DB 버전 확인 중"

        //firebase 설정
        val database: FirebaseDatabase?
        val firebaseVersion: DatabaseReference?
        val firebaseTimetableData: DatabaseReference?

        //Preferences 설정
        val versionPreferences = getSharedPreferences("version", Context.MODE_PRIVATE)
        var version = versionPreferences.getString("version", "202107271830")
        TimeTableSelPref.prefs.setInt("majorSel", 0)
        TimeTableSelPref.prefs.setInt("gradeSel", 0)

        //Room 설정
        val db = TimeTableDatabase.getInstance(applicationContext)

        //기타
        val localDataList = mutableListOf<TimeTableData>()
        var originData: String
        var done = false
        var update: Boolean? = null

        // 로그인 유지
        if (PreferenceManager.getBoolean(this, REMEMBER_LOGIN)) {
            User.login()
        } else {
            User.logout()
        }

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
        }.addOnFailureListener {
            binding.showProgress.text = "시간표 DB 버전 확인 실패"
            startActivity(intent)
        }

        firebaseTimetableData = database.getReference("uswTimetable")

        CoroutineScope(IO).launch {
            while (true) {
                if (update != null && !toUpdate)
                    break
                delay(500L)
                Log.d("update123","$update")
            } //update 값이 입력될 때 까지 무한 루프
            if (update == true) {
                Log.d("firebase", "업데이트 실행 $version")
                withContext(Main){
                    binding.showProgress.text = "서버로부터 시간표 DB 불러오는 중"
                }
                firebaseTimetableData.get().addOnSuccessListener {
                    var index = 1L
                    for(data in it.children) {
                        val emptyData = TimeTableData()
                        val tempData = data.value as HashMap<*, *>
                        emptyData.number = index
                        emptyData.major = tempData["estbDpmjNm"].toString()
                        emptyData.grade = tempData["trgtGrdeCd"].toString() + "학년"
                        emptyData.classNumber = tempData["subjtCd"].toString()
                        emptyData.classDivideNumber = tempData["diclNo"].toString()
                        emptyData.className = tempData["subjtNm"].toString()
                        emptyData.classification = tempData["facDvnm"].toString()
                        emptyData.professor = tempData["reprPrfsEnoNm"]?.toString() ?: "None"
                        emptyData.time = tempData["timtSmryCn"]?.toString() ?: "None"
                        emptyData.credit = tempData["point"].toString()
                        index++
                        Log.d("arrayTest", "$emptyData")
                        localDataList.add(emptyData)
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
}
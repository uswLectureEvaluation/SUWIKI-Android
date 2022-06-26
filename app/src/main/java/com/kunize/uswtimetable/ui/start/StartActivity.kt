package com.kunize.uswtimetable.ui.start

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kunize.uswtimetable.data.local.OpenMajorData
import com.kunize.uswtimetable.data.local.OpenMajorDatabase
import com.kunize.uswtimetable.data.local.TimeTableData
import com.kunize.uswtimetable.data.local.TimeTableDatabase
import com.kunize.uswtimetable.databinding.ActivityStartBinding
import com.kunize.uswtimetable.repository.open_major.OpenMajorRemoteDataSource
import com.kunize.uswtimetable.repository.open_major.OpenMajorRepository
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.common.User
import com.kunize.uswtimetable.ui.login.LoginActivity.Companion.REMEMBER_LOGIN
import com.kunize.uswtimetable.ui.main.MainActivity
import com.kunize.uswtimetable.util.PreferenceManager
import com.kunize.uswtimetable.util.TimeTableSelPref
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private lateinit var versionPreferences: SharedPreferences
    var toUpdate = false

    companion object {
        const val MY_REQUEST_CODE = 700
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.showProgress.text = "시간표 DB 버전 확인 중"
        setPercentage(0)

        //retrofit2
        val apiService = IRetrofit.getInstanceWithNoToken()
        val openMajorRepository = OpenMajorRepository(OpenMajorRemoteDataSource(apiService))

        //firebase 설정
        val database: FirebaseDatabase?
        val firebaseVersion: DatabaseReference?
        val firebaseTimetableData: DatabaseReference?

        //Preferences 설정
        versionPreferences = getSharedPreferences("version", Context.MODE_PRIVATE)
        var version = versionPreferences.getString("version", "202107271830")
        val openMajorVersion = versionPreferences.getFloat("openMajorVersion", 0f)
        TimeTableSelPref.prefs.setInt("gradeSel", 0)
        TimeTableSelPref.prefs.setString("openMajorSel", "전체")

        //Room 설정
        val db = TimeTableDatabase.getInstance(applicationContext)

        //기타
        val localDataList = mutableListOf<TimeTableData>()
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
            binding.showProgress.text = "시간표 DB 버전 확인 완료"
            setPercentage(25)
        }.addOnFailureListener {
            binding.showProgress.text = "시간표 DB 버전 확인 실패"
            setPercentage(100)
            startActivity(intent)
        }

        firebaseTimetableData = database.getReference("uswTimetable")

        CoroutineScope(IO).launch {
            while (true) {
                if (update != null && !toUpdate)
                    break
                delay(500L)
                Log.d("update123", "$update")
            } //update 값이 입력될 때 까지 무한 루프
            if (update == true) {
                Log.d("firebase", "업데이트 실행 $version")
                withContext(Main) {
                    binding.showProgress.text = "서버로부터 시간표 DB 불러오는 중"
                }
                firebaseTimetableData.get().addOnSuccessListener {
                    var index = 1L
                    for (data in it.children) {
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
                        withContext(Main) {
                            binding.showProgress.text = "시간표 DB 저장 중"
                        }
                        for (i in localDataList.indices) {
                            db.timetableDao().insert(localDataList[i])
                            val percent = 25 + ((i.toDouble() / localDataList.size) * 70).toInt()
                            withContext(Main) {
                                setPercentage(percent)
                            }
                        }
                        versionPreferences.edit(true) {
                            putString("version", version)
                        }
                        break
                    } //시간표를 업데이트 완료 전까지 무한 루프
                    delay(1000L)
                }
                withContext(Main) {
                    updateOpenMajorList(openMajorRepository, openMajorVersion)
                    setPercentage(100)
                }
                startActivity(intent)
            } else {
                withContext(Main) {
                    updateOpenMajorList(openMajorRepository, openMajorVersion)
                    setPercentage(100)
                }
                startActivity(intent)
            }
        }
    }

    private suspend fun updateOpenMajorList(
        openMajorRepository: OpenMajorRepository,
        openMajorVersion: Float
    ) {
        when (val majorVersionResponse = openMajorRepository.getOpenMajorVersion()) {
            is ApiResponse.Success -> {
                if (majorVersionResponse.data.version <= openMajorVersion) return
                val majorListResponse = openMajorRepository.getOpenMajorList()
                if(majorListResponse is ApiResponse.Success) {
                    withContext(IO) {
                        val db = OpenMajorDatabase.getInstance(applicationContext)
                        db!!.openMajorDao().deleteAll()
                        val data = majorListResponse.data.convertToOpenMajorData()
                        data.add(0, OpenMajorData("전체"))
                        db.openMajorDao().insertAll(data)
                        versionPreferences.edit {
                            putFloat("openMajorVersion", majorVersionResponse.data.version)
                        }
                    }
                }
            }
            else -> {}
        }
    }

    private fun setPercentage(percentage: Int) {
        binding.tvProgress.text = "$percentage%"
        binding.progressBar.progress = percentage
    }
}
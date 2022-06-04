package com.kunize.uswtimetable.ui.home

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.kunize.uswtimetable.*
import com.kunize.uswtimetable.ui.home.timetable.BitmapConverter.bitmapToString
import com.kunize.uswtimetable.ui.home.timetable.DBManager.getCurrentTimetableInfo
import com.kunize.uswtimetable.ui.home.timetable.DBManager.jsonToArray
import com.kunize.uswtimetable.ui.home.timetable.UswTimeTable.Companion.CLASSNAME_LOCATION
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.FragmentHomeBinding
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.ui.add_class.AddClassActivity
import com.kunize.uswtimetable.ui.home.timetable.TimeTableWidget
import com.kunize.uswtimetable.ui.timetable_list.TimeTableListActivity
import com.kunize.uswtimetable.ui.timetable_setting.SettingActivity
import com.kunize.uswtimetable.util.TimeTableSelPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    lateinit var db: TimeTableListDatabase
    lateinit var timeTableList: List<TimeTableList>
    private var timeTableSel: TimeTableList? = null
    private var tempTimeData = mutableListOf<TimeData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.toolBar.inflateMenu(R.menu.home_menu) // AppBar에 메뉴 설정
        binding.toolBar.overflowIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_setting_line)

        db = TimeTableListDatabase.getInstance(requireActivity().applicationContext)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // AppBar 메뉴 클릭 리스너
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add_class -> {
                    if (timeTableSel == null) {
                        Toast.makeText(activity, "시간표를 생성해주세요", Toast.LENGTH_SHORT).show()
                        false
                    } else {
                        val intent = Intent(activity, AddClassActivity::class.java)
                        startActivity(intent)
                        true
                    }
                }
                R.id.action_setting -> {
                    if (timeTableSel == null) {
                        Toast.makeText(activity, "시간표를 생성해주세요", Toast.LENGTH_SHORT).show()
                        false
                    } else {
                        val intent = Intent(activity, SettingActivity::class.java)
                        startActivity(intent)
                        true
                    }
                }
                R.id.action_show_list -> {
                    val intent = Intent(activity, TimeTableListActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(IO).launch {
            timeTableList = db.timetableListDao().getAll()
            tempTimeData.clear()

            if (timeTableList.isEmpty()) notExistTimetable()
            else existTimetable()

            withContext(Main) {
                delay(200L)
                try { widgetUpdate() } catch (e: Exception) { }
            }
        }
    }

    private suspend fun existTimetable() {
        timeTableSel = getCurrentTimetableInfo(db)

        binding.uswTimeTable.isEmpty = false
        tempTimeData = jsonToArray(timeTableSel!!.timeTableJsonData)
        binding.uswTimeTable.timeTableData = tempTimeData
        binding.uswTimeTable.infoFormat = TimeTableSelPref.prefs.getInt(
            "infoFormat",
            CLASSNAME_LOCATION
        )
        withContext(Main) {
            binding.textTitle.text = timeTableSel?.timeTableName
            binding.uswTimeTable.drawTable()
        }
    }

    private suspend fun notExistTimetable() {
        timeTableSel = null
        binding.uswTimeTable.isEmpty = true
        withContext(Main) {
            binding.textTitle.text = ""
            binding.uswTimeTable.drawTable()
        }
    }

    private fun widgetUpdate() {
        val timetableBitmap = viewToBitmap(binding.uswTimeTable)
        val strBit = bitmapToString(timetableBitmap)
        TimeTableSelPref.prefs.setString("image", strBit)
        val intentAction = Intent(activity, TimeTableWidget::class.java)
        intentAction.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(activity)
            .getAppWidgetIds(ComponentName(requireActivity(), TimeTableWidget::class.java))
        intentAction.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        requireActivity().sendBroadcast(intentAction)
    }

    private fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }
}
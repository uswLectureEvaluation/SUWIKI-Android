package com.kunize.uswtimetable.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.dao_database.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ItemRecyclerTimetableBinding
import com.kunize.uswtimetable.dataclass.TimeTableList
import com.kunize.uswtimetable.dialog.EditTimetableDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimeTableListAdapter : RecyclerView.Adapter<TimeTableListAdapter.Holder>() {
    var timeTableListData = mutableListOf<TimeTableList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerTimetableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = timeTableListData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,data)
        }
    }

    override fun getItemCount(): Int {
        return timeTableListData.size
    }

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, data: TimeTableList)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class Holder(private val binding: ItemRecyclerTimetableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: TimeTableList) {
            binding.timeTableYearSemester.text = data.year + "년 " + data.semester + "학기"
            binding.timeTableName.text = data.timeTableName
            binding.btnEdit.setOnClickListener {
                Log.d("test","클릭됨")
                val dlg = EditTimetableDialog(binding.root.context)
                dlg.setOnOKClickedListener { name ->
                    binding.timeTableName.text = name
                    CoroutineScope(IO).launch {
                        data.timeTableName = name
                        val db = TimeTableListDatabase.getInstance(binding.root.context)!!
                        db.timetableListDao().update(data)
                        withContext(Main) {
                            notifyDataSetChanged()
                        }
                    }
                }
                dlg.start()
            }
            binding.btnDelete.setOnClickListener {
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setMessage("\"" + data.timeTableName + "\"" + " 시간표가 삭제됩니다.")
                builder.setPositiveButton(
                    "네"
                ) { dialog: DialogInterface?, which: Int ->
                    //TODO DB에서 삭제
                    CoroutineScope(IO).launch {
                        val db = TimeTableListDatabase.getInstance(binding.root.context)!!
                        db.timetableListDao().delete(data)
                        timeTableListData.remove(data)
                        withContext(Main) {
                            notifyItemRemoved(position)
                        }
                    }
                }
                builder.setNegativeButton(
                    "취소"
                ) { dialog: DialogInterface?, which: Int ->  }
                builder.show()
            }
        }
    }
}


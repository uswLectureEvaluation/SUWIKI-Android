package com.kunize.uswtimetable.ui.timetable_list

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.data.local.TimeTableListDatabase
import com.kunize.uswtimetable.databinding.ItemRecyclerTimetableBinding
import com.kunize.uswtimetable.data.local.TimeTableList
import com.kunize.uswtimetable.util.TimeTableListClickType
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
            itemClickListener.onClick(it,data, TimeTableListClickType.ITEM_CLICK)
        }
    }

    override fun getItemCount(): Int {
        return timeTableListData.size
    }

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, data: TimeTableList, type: TimeTableListClickType)
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
                itemClickListener.onClick(it,data, TimeTableListClickType.EDIT_CLICK)
            }
            binding.btnDelete.setOnClickListener {
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setMessage("\"" + data.timeTableName + "\"" + " 시간표가 삭제됩니다.")
                builder.setPositiveButton(
                    "네"
                ) { dialog: DialogInterface?, which: Int ->
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


package com.kunize.uswtimetable.ui.home.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.ui.home.timetable.DBManager.deleteTime
import com.kunize.uswtimetable.data.local.TimeData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class BottomSheet(data: TimeData, val callback: (Int) -> Unit) : BottomSheetDialogFragment()
{
    lateinit var edit: TextView
    lateinit var delete: TextView
    private var localData: TimeData = data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.bottomName)?.text = localData.name
        view.findViewById<TextView>(R.id.bottomProfessor)?.text = localData.professor
        view.findViewById<TextView>(R.id.bottomTime)?.text = localData.location + " (" + localData.day + " " + localData.startTime + "~" +localData.endTime + ")"
        edit = view.findViewById<TextView>(R.id.bottomEdit)!!
        edit.setOnClickListener{
            callback(1)
            dismiss()
        }
        delete = view.findViewById<TextView>(R.id.bottomDelete)!!
        delete.setOnClickListener {
            callback(2)
            CoroutineScope(IO).launch {
                deleteTime(requireActivity().applicationContext, localData)
                dismiss()
            }
        }
    }
}
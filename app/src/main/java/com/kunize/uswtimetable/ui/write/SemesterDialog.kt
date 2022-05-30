package com.kunize.uswtimetable.ui.write

import android.app.Dialog
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.DialogSpinnerSemesterBinding

class SemesterDialog(
    private val context: AppCompatActivity, private val viewModel: WriteViewModel,
    private val spinnerList: List<String>
) {
    private lateinit var binding: DialogSpinnerSemesterBinding
    private val dlg = Dialog(context)

    fun show() {
        binding = DialogSpinnerSemesterBinding.inflate(context.layoutInflater)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        dlg.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dlg.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val adapter = SemesterListAdapter(viewModel)
        adapter.submitList(spinnerList)
        binding.rv.adapter = adapter

        dlg.show()
    }

    fun dismiss() {
        dlg.dismiss()
    }
}
package com.kunize.uswtimetable.ui.search_result

import android.app.Dialog
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.databinding.DialogSpinnerSortBinding

class SortDialog(
    private val context: AppCompatActivity, private val viewModel: SearchResultViewModel,
    private val spinnerList: List<String>
) {
    private lateinit var binding: DialogSpinnerSortBinding
    private val dlg = Dialog(context)

    fun show() {
        binding = DialogSpinnerSortBinding.inflate(context.layoutInflater)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        dlg.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dlg.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val adapter = SortListAdapter(viewModel)
        adapter.submitList(spinnerList)
        binding.rv.adapter = adapter

        dlg.show()
    }

    fun dismiss() {
        dlg.dismiss()
    }
}
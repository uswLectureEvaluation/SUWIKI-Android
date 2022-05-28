package com.kunize.uswtimetable.ui.evaluation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.DialogSortBinding

class ImageSortDialog(private val context: AppCompatActivity, private val viewModel: EvaluationViewModel) {
    private lateinit var binding: DialogSortBinding
    private val dlg = Dialog(context)

    fun show() {
        binding = DialogSortBinding.inflate(context.layoutInflater)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        dlg.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dlg.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.viewModel = viewModel

        dlg.show()
    }

    fun dismiss() {
        dlg.dismiss()
    }
}
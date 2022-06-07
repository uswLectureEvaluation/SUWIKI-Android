package com.kunize.uswtimetable.ui.class_info

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.DialogDaySortBinding
import com.kunize.uswtimetable.databinding.DialogGradeSortBinding
import com.kunize.uswtimetable.databinding.DialogSortBinding

class DaySortDialog(private val context: AppCompatActivity): View.OnClickListener {
    private lateinit var binding: DialogDaySortBinding
    private lateinit var itemClickListener: ItemClickListener
    private val dlg = Dialog(context)

    interface ItemClickListener {
        fun onClick(text: String)
    }

    fun show() {
        binding = DialogDaySortBinding.inflate(context.layoutInflater)
        binding.tvNone.setOnClickListener(this)
        binding.tvMon.setOnClickListener(this)
        binding.tvTue.setOnClickListener(this)
        binding.tvWed.setOnClickListener(this)
        binding.tvThu.setOnClickListener(this)
        binding.tvFri.setOnClickListener(this)
        binding.tvSat.setOnClickListener(this)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)
        dlg.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dlg.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dlg.show()
    }

    fun setDialogListener(dialogListener : ItemClickListener) {
        this.itemClickListener = dialogListener
    }

    override fun onClick(view: View?) {
        itemClickListener.onClick((view as TextView).text.toString())
        dlg.dismiss()
    }
}
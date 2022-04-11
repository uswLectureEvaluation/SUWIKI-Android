package com.kunize.uswtimetable.ui.search_result

import android.widget.RadioButton
import androidx.databinding.BindingAdapter

object SearchResultBindingAdapter {
    @BindingAdapter("radioBtnClicked")
    @JvmStatic
    fun setCheck(radioButton: RadioButton, isChecked: Boolean) {
        radioButton.isChecked = isChecked
        if(isChecked)
            radioButton.text = radioButton.text.toString() + " ↑"
        else
            radioButton.text = radioButton.text.toString().replace(" ↑", "")
    }
}
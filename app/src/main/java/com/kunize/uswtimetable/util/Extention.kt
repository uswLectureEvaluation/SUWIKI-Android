package com.kunize.uswtimetable.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment

fun EditText.afterTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            completion(p0)
        }
    })
}

fun NestedScrollView.infiniteScrolls(doScrollBottom: () -> Unit) {
    this.setOnScrollChangeListener { v, _, _, _, _ ->
        if(!v.canScrollVertically(1)) {
            //viewModel.deleteLoading()를 호출 해 로딩 바 제거
            //val newData = 서버에서 새 데이터를 받아오는 로직
            //viewModel.addData(받아온 데이터 추가)
            doScrollBottom()
        }
    }
}

fun RecyclerView.infiniteScrolls(doScrollBottom: () -> Unit) {
    this.addOnScrollListener((object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
            // 스크롤이 끝에 도달했는지 확인
            if (lastVisibleItemPosition == itemTotalCount) {
                //viewModel.deleteLoading()를 호출 해 로딩 바 제거
                //val newData = 서버에서 새 데이터를 받아오는 로직
                //viewModel.addData(받아온 데이터 추가)
                doScrollBottom()
            }
        }
    }))

}

fun SeekBar.seekbarChangeListener(doChangeProgress: (progress: Float) -> Unit) {
    this.setOnSeekBarChangeListener((object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if(progress < 1)
                progress = 1
            doChangeProgress(progress.toFloat() / 2)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }
    }))
}

// 문자열 -> json 형태인지 json 배열 형태인지
fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
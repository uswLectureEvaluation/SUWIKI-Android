package com.kunize.uswtimetable.util

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

fun EditText.afterTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            completion(p0)
        }
    })
}

fun EditText.onTextChanged(completion: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            completion(p0)
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun EditText.afterEditTextChanged(completion: (Editable?) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            val newInput = p0.toString()
            debounceJob?.cancel()

            lastInput = newInput
            debounceJob = uiScope.launch {
                delay(200L)
                if (lastInput == newInput)
                    completion(p0)
            }

        }
    })
}

fun NestedScrollView.infiniteScrolls(doScrollBottom: () -> Unit) {
    this.setOnScrollChangeListener { v, _, _, _, _ ->
        if (!v.canScrollVertically(1)) {
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

            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
            if (lastVisibleItemPosition == itemTotalCount) {
                Log.d("infiniteScrolls", "하단 감지")
                doScrollBottom()
            }
        }
    }))

}

fun SeekBar.seekbarChangeListener(doChangeProgress: (progress: Float) -> Unit) {
    this.setOnSeekBarChangeListener((object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (progress < 1)
                progress = 1
            doChangeProgress(progress.toFloat() / 2)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }
    }))
}

fun Spinner.onItemSelected(doSpinnerSelected: (position: Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            doSpinnerSelected(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}

// 문자열 -> json 형태인지 json 배열 형태인지
fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
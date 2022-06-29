package com.kunize.uswtimetable.util.extensions

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.infiniteScrolls(doScrollBottom: () -> Unit) {
    this.addOnScrollListener(
        (
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                    val itemTotalCount =
                        recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
                    if (lastVisibleItemPosition == itemTotalCount) {
                        Log.d("infiniteScrolls", "하단 감지")
                        doScrollBottom()
                    }
                }
            }
            )
    )
}

package com.kunize.uswtimetable.ui.search_result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemRecyclerLectureListBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerProgressBinding
import com.kunize.uswtimetable.util.LectureItemViewType
import com.suwiki.domain.model.LectureMain

class SearchResultAdapter(val onItemClicked: (id: Long) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var evaluationListData = mutableListOf<LectureMain?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LectureItemViewType.SHORT -> {
                val binding = ItemRecyclerLectureListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LectureSearchHolder(binding)
            }

            else -> {
                val binding = ItemRecyclerProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                LoadingHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        evaluationListData[position]?.let {
            return LectureItemViewType.SHORT
        }
        return LectureItemViewType.LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = evaluationListData[position]
        data?.let {
            (holder as LectureSearchHolder).setData(data)
        }
    }

    override fun getItemCount(): Int = evaluationListData.size

    inner class LoadingHolder(private val binding: ItemRecyclerProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LectureSearchHolder(private val binding: ItemRecyclerLectureListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: LectureMain) {
            with(binding) {
                this.data = data
                tvDetail.setOnClickListener {
                    if (clDetail.isVisible) {
                        tvDetail.setText(R.string.see_detail)
                        clDetail.visibility = View.GONE
                    } else {
                        tvDetail.setText(R.string.see_short)
                        clDetail.visibility = View.VISIBLE
                    }
                }
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    itemView.setOnClickListener {
                        onItemClicked(data.id)
                    }
                }
                executePendingBindings()
            }
        }
    }
}

package com.kunize.uswtimetable.ui.evaluation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.ItemRecyclerLectureListBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerProgressBinding
import com.kunize.uswtimetable.databinding.ItemRecyclerSeeMoreFooterBinding
import com.kunize.uswtimetable.util.LectureItemViewType
import com.suwiki.domain.model.LectureMain

class EvaluationFooterAdapter(val onItemClicked: (id: Long) -> Unit) :
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

            LectureItemViewType.FOOTER -> {
                val binding = ItemRecyclerSeeMoreFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                FooterHolder(binding)
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
        if (position == evaluationListData.size) {
            return LectureItemViewType.FOOTER
        }
        evaluationListData[position]?.let {
            return LectureItemViewType.SHORT
        }
        return LectureItemViewType.LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == evaluationListData.size) {
            (holder as FooterHolder).setData()
        } else {
            val data = evaluationListData[position]
            data?.let {
                (holder as LectureSearchHolder).setData(data)
            }
        }
    }

    override fun getItemCount(): Int = evaluationListData.size + 1

    inner class LoadingHolder(private val binding: ItemRecyclerProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class FooterHolder(private val binding: ItemRecyclerSeeMoreFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData() {
            itemView.setOnClickListener {
                onItemClicked(LectureItemViewType.FOOTER.toLong())
            }
        }
    }

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

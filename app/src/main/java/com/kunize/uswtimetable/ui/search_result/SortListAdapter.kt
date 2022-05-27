package com.kunize.uswtimetable.ui.search_result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.ItemDialogSpinnerBinding

class SortListAdapter(private val viewModel: SearchResultViewModel) : ListAdapter<String, SortListAdapter.SortViewHolder>(diffUtil) {
    inner class SortViewHolder(private val binding: ItemDialogSpinnerBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(text: String) {
                binding.viewModel = viewModel
                binding.text = text
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        return SortViewHolder(ItemDialogSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
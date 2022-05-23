package com.kunize.uswtimetable.ui.select_open_major

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.OpenMajorItem
import com.kunize.uswtimetable.databinding.ItemRecyclerOpenMajorBinding
import java.util.*

class SelectOpenMajorAdapter : RecyclerView.Adapter<SelectOpenMajorAdapter.SearchHolder>(), Filterable {
    var filteredData = mutableListOf<OpenMajorItem>()
    var unfilteredData = mutableListOf<OpenMajorItem>()
    var selectedItemTitle = ""
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = ItemRecyclerOpenMajorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val data = filteredData[position]
        holder.setData(data, position)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, data)
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredData = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredData.addAll(unfilteredData)
            } else {
                for (item in unfilteredData) {
                    if (item.title.lowercase(Locale.getDefault())
                            .contains(constraint.toString().lowercase(Locale.getDefault()))
                    ) {
                        filteredData.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredData
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredData = results.values as MutableList<OpenMajorItem>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface ItemClickListener {
        fun onClick(view: View, data: OpenMajorItem)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class SearchHolder(private val binding: ItemRecyclerOpenMajorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun setData(data: OpenMajorItem, position: Int) {
            if(selectedItemTitle == data.title) {
                binding.layout.setBackgroundColor(binding.root.context.getColor(R.color.suwiki_blue_100))
            } else {
                binding.layout.setBackgroundColor(binding.root.context.getColor(R.color.suwiki_white))
            }

            binding.tvTitle.setOnClickListener {
                selectedItemTitle = data.title
                notifyDataSetChanged()
            }
            binding.data = data
            binding.cbStar.setOnClickListener {
                filteredData.find { it.title == data.title }!!.isChecked = binding.cbStar.isChecked
                unfilteredData.find { it.title == data.title }!!.isChecked = binding.cbStar.isChecked
            }
        }
    }
}


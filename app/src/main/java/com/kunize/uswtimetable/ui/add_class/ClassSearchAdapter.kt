package com.kunize.uswtimetable.ui.add_class

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.util.HangulUtils
import com.kunize.uswtimetable.databinding.ItemRecyclerClassInfoBinding
import com.kunize.uswtimetable.data.local.TimeTableData
import java.util.*

class ClassSearchAdapter : RecyclerView.Adapter<SearchHolder>(), Filterable {
    var filteredData = mutableListOf<TimeTableData>()
    var unfilteredData = mutableListOf<TimeTableData>()
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = ItemRecyclerClassInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val data = filteredData[position]
        holder.setData(data)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, data)
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filteredData = mutableListOf<TimeTableData>()
            if (constraint == null || constraint.isEmpty()) {
                filteredData.addAll(unfilteredData)
            } else {
                for (item in unfilteredData) {
                    val iniName =
                        HangulUtils.getHangulInitialSound(item.className, constraint.toString());
                    if (iniName.indexOf(constraint.toString()) >= 0) { // 초성검색어가 있으면 해당 데이터 리스트에 추가
                        filteredData.add(item)
                    } else if (item.className.lowercase(Locale.getDefault())
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

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredData = results.values as MutableList<TimeTableData>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    interface ItemClickListener {
        fun onClick(view: View, data: TimeTableData)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}

class SearchHolder(private val binding: ItemRecyclerClassInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(data: TimeTableData) {
        binding.className.text = data.className
        binding.major.text = data.major
        binding.time.text = data.time
        binding.etc.text = data.grade + ", " + data.classification + ", " + data.professor
    }
}
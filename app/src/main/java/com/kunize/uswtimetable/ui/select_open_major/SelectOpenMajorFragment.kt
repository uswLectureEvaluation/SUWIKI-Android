package com.kunize.uswtimetable.ui.select_open_major

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.OpenMajorDatabase
import com.kunize.uswtimetable.data.local.OpenMajorItem
import com.kunize.uswtimetable.databinding.FragmentSelectOpenMajorBinding
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.search_result.SearchResultViewModel
import com.kunize.uswtimetable.util.afterEditTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectOpenMajorFragment : Fragment() {

    private lateinit var binding: FragmentSelectOpenMajorBinding
    private lateinit var adapter: SelectOpenMajorAdapter
    private val viewModel: SelectOpenMajorViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_open_major, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SelectOpenMajorAdapter()
        val db = OpenMajorDatabase.getInstance(requireContext())
        CoroutineScope(IO).launch {
            val temp = db!!.openMajorDao().getAll()
            val data = mutableListOf<OpenMajorItem>()
            temp.forEach {
                data.add(OpenMajorItem(false, it.name))
            }
            adapter.filteredData = data
            adapter.unfilteredData = data
            adapter.setHasStableIds(true)
            withContext(Main) {
                binding.rvOpenMajor.adapter = adapter
                binding.rvOpenMajor.layoutManager = LinearLayoutManager(requireContext())
                binding.rvOpenMajor.itemAnimator = null
                binding.etSearch.afterEditTextChanged {
                    adapter.filter.filter(it)
                }
            }
        }

        adapter.setItemClickListener(object : SelectOpenMajorAdapter.ItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(view: View, data: OpenMajorItem) {
                adapter.selectedItemTitle = data.title
                adapter.notifyDataSetChanged()
            }
        })
    }
}
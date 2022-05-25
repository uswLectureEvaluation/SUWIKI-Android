package com.kunize.uswtimetable.ui.select_open_major

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.OpenMajorData
import com.kunize.uswtimetable.data.local.OpenMajorDatabase
import com.kunize.uswtimetable.data.local.OpenMajorItem
import com.kunize.uswtimetable.databinding.FragmentSelectOpenMajorBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.user_info.User
import com.kunize.uswtimetable.util.afterEditTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectOpenMajorFragment : Fragment() {

    private lateinit var binding: FragmentSelectOpenMajorBinding
    private lateinit var adapter: SelectOpenMajorAdapter
    private lateinit var tempOpenMajorList: List<OpenMajorData>
    var openMajorList = mutableListOf<OpenMajorItem>()
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
        adapter = SelectOpenMajorAdapter(viewModel)
        val db = OpenMajorDatabase.getInstance(requireContext())

        CoroutineScope(IO).launch {
            tempOpenMajorList = db!!.openMajorDao().getAll()
            getAllMajorList()
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

        binding.rbAll.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) CoroutineScope(IO).launch {
                getAllMajorList()
                withContext(Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.rbBookmark.setOnCheckedChangeListener { _, isChecked ->
            if(User.isLoggedIn.value == false) {
                return@setOnCheckedChangeListener
            }
            if(isChecked) CoroutineScope(IO).launch {
                getBookmarkList()
                withContext(Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.starClickEvent.observe(viewLifecycleOwner, EventObserver { title ->
            if (User.isLoggedIn.value == true) {
                val filteredData = adapter.filteredData
                val unfilteredData = adapter.unfilteredData
                val filteredDataIndex = filteredData.indexOfFirst { it.title == title }
                val unfilteredDataIndex = unfilteredData.indexOfFirst { it.title == title }

                if (filteredDataIndex == -1 || unfilteredDataIndex == -1)
                    return@EventObserver

                val checkedType = !filteredData[filteredDataIndex].isChecked
                filteredData[filteredDataIndex].isChecked = checkedType
                unfilteredData[unfilteredDataIndex].isChecked = checkedType

                CoroutineScope(IO).launch {
                    if (checkedType) {
                        viewModel.bookmarkMajor("\"$title\"")
                    } else {
                        viewModel.clearBookmarkMajor("\"$title\"")
                    }
                }
            }
        })

        adapter.setItemClickListener(object : SelectOpenMajorAdapter.ItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(view: View, data: OpenMajorItem) {
                adapter.selectedItemTitle = data.title
                adapter.notifyDataSetChanged()
            }
        })
    }

    private suspend fun getAllMajorList() {
        val tempBookmarkMajorList = viewModel.getBookmarkList()
        openMajorList = mutableListOf()
        tempOpenMajorList.forEach {
            openMajorList.add(OpenMajorItem(it.name in tempBookmarkMajorList, it.name))
        }
        adapter.filteredData = openMajorList
        adapter.unfilteredData = openMajorList
    }

    private suspend fun getBookmarkList() {
        val tempBookmarkMajorList = viewModel.getBookmarkList()
        openMajorList = mutableListOf()
        tempBookmarkMajorList.forEach {
            openMajorList.add(OpenMajorItem(true, it))
        }
        adapter.filteredData = openMajorList
        adapter.unfilteredData = openMajorList
    }
}
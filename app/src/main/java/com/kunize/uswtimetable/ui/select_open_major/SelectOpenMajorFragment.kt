package com.kunize.uswtimetable.ui.select_open_major

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import androidx.recyclerview.widget.SimpleItemAnimator
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.data.local.OpenMajorDatabase
import com.kunize.uswtimetable.data.local.OpenMajorItem
import com.kunize.uswtimetable.databinding.FragmentSelectOpenMajorBinding
import com.kunize.uswtimetable.util.afterEditTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectOpenMajorFragment : Fragment() {

    private lateinit var binding: FragmentSelectOpenMajorBinding
    private lateinit var adapter: SelectOpenMajorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_open_major, container, false)
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
                binding.rvOpenMajor.visibility = View.INVISIBLE
                binding.etSearch.afterEditTextChanged {
                    if (binding.etSearch.text.toString().isEmpty()) {
                        binding.rvOpenMajor.visibility = View.INVISIBLE
                    } else {
                        binding.rvOpenMajor.visibility = View.VISIBLE
                    }
                    adapter.filter.filter(it)
                }
            }
        }

        adapter.setItemClickListener(object : SelectOpenMajorAdapter.ItemClickListener {
            override fun onClick(view: View, data: OpenMajorItem) {
                //
            }
        })
    }
}
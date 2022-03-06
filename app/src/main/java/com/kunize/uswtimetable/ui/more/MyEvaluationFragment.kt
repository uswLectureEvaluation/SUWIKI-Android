package com.kunize.uswtimetable.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunize.uswtimetable.adapter.MyEvaluationAdapter
import com.kunize.uswtimetable.databinding.FragmentMyEvaluationBinding
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.ui.lecture_info.LectureInfoFragmentDirections
import com.kunize.uswtimetable.util.ItemType

class MyEvaluationFragment : Fragment() {
    private var _binding: FragmentMyEvaluationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyEvaluationAdapter
    private val viewModel: MyPostViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyEvaluationBinding.inflate(inflater, container, false)

        adapter = MyEvaluationAdapter { data, type ->
            when (type) {
                ItemType.ROOT_VIEW -> {
                    makeToast("$data 선택됨")
                }
                ItemType.EDIT_BUTTON -> {
                    makeToast("${data.id} 아이템 수정 클릭")
                    gotoWriteFragment(data)
                }
                ItemType.DELETE_BUTTON -> {
                    makeToast("${data.id} 아이템 삭제 클릭")
                }
            }
        }

        initRecyclerView()

        return _binding?.root
    }

    private fun gotoWriteFragment(data: MyEvaluation) {
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(
                myEvaluation = data,
                isEvaluation = true
            )
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        binding.myEvaluationList.adapter = adapter
        binding.myEvaluationList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.myEvaluationData.observe(viewLifecycleOwner) { evaluations ->
            adapter.submitList(evaluations)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun makeToast(message: String) {
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
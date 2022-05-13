package com.kunize.uswtimetable.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.FragmentMyEvaluationBinding
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.ItemType
import com.kunize.uswtimetable.util.infiniteScrolls

class MyEvaluationFragment : Fragment() {
    private var _binding: FragmentMyEvaluationBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyEvaluationAdapter
    private val viewModel: MyEvaluationViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyEvaluationBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = MyEvaluationAdapter { data, type ->
            when (type) {
                ItemType.ROOT_VIEW -> {
//                    makeToast("$data 선택됨")
                }
                ItemType.EDIT_BUTTON -> {
//                    makeToast("${data.id} 아이템 수정 클릭")
                    gotoWriteFragment(data)
                }
                ItemType.DELETE_BUTTON -> {
                    makeToast("${data.id} 아이템 삭제 - 사용자에게 확인하는 메시지 보여줘야함")
                    viewModel.deletePost(data.id)
                }
            }
        }

        initRecyclerView()

        return _binding?.root
    }

    private fun gotoWriteFragment(data: MyEvaluationDto) {
        val action =
            MyEvaluationFragmentDirections.actionGlobalWriteFragment(
                lectureId = data.id,
                myEvaluation = data,
                isEvaluation = true
            )
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        recyclerView = binding.myEvaluationList
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel.myEvaluationData.observe(viewLifecycleOwner) { evaluations ->
            adapter.submitList(evaluations)
        }
        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        toast?.cancel()
    }

    private fun makeToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
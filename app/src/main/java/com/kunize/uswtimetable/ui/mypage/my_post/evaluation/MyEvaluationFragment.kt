package com.kunize.uswtimetable.ui.mypage.my_post.evaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.FragmentMyEvaluationBinding
import com.kunize.uswtimetable.dataclass.MyEvaluationDto
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.common.ViewModelFactory
import com.kunize.uswtimetable.util.ItemType
import com.kunize.uswtimetable.util.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

class MyEvaluationFragment : Fragment() {
    private var _binding: FragmentMyEvaluationBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyEvaluationAdapter
    private val viewModel: MyEvaluationViewModel by viewModels { ViewModelFactory() }
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyEvaluationBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = MyEvaluationAdapter(viewModel)

        initRecyclerView()

        viewLifecycleOwner.repeatOnStarted {
            adapter.loadStateFlow.collect {
                binding.loadingMyEvaluation.isVisible = it.source.refresh is LoadState.Loading
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.items.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.eventClicked.observe(viewLifecycleOwner, EventObserver { (type, data) ->
            when (type) {
                ItemType.ROOT_VIEW -> {}
                ItemType.EDIT_BUTTON -> { gotoWriteFragment(data) }
                ItemType.DELETE_BUTTON -> {
                    makeToast("${data.id} 아이템 삭제 - 사용자에게 확인하는 메시지 보여줘야함")
                    viewModel.deletePost(data.id)
                }
            }
        })
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
        /*viewModel.myEvaluationData.observe(viewLifecycleOwner) { evaluations ->
            adapter.submitList(evaluations)
        }
        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }*/
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

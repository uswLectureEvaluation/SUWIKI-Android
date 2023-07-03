package com.kunize.uswtimetable.ui.mypage.mypost.evaluation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.FragmentMyEvaluationBinding
import com.kunize.uswtimetable.ui.mypage.mypost.MyPostResult
import com.kunize.uswtimetable.util.UserPoint.DELETE_POST
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast
import com.suwiki.domain.model.MyEvaluation
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MyEvaluationFragment : Fragment() {
    private var _binding: FragmentMyEvaluationBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyEvaluationAdapter
    private val viewModel: MyEvaluationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            viewModel.uiEvent.collect { event ->
                handleEvent(event)
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.isLoggedIn.collect {
                if (!it) {
                    // TODO 로그아웃 처리
                }
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.resultFlow.collect { result ->
                if (result == MyPostResult.Fail) {
                    if (viewModel.userInfo.value.point < abs(DELETE_POST)) {
                        this@MyEvaluationFragment.toast("포인트가 부족합니다")
                    } else {
                        this@MyEvaluationFragment.toast("삭제할 수 없습니다")
                    }
                }
            }
        }
    }

    private fun gotoWriteFragment(data: MyEvaluation) {
        val action =
            MyEvaluationFragmentDirections.actionGlobalWriteFragment(
                lectureId = data.id,
                myEvaluation = data,
                isEvaluation = true,
            )
        findNavController().navigate(action)
    }

    private fun initRecyclerView() {
        recyclerView = binding.rvMyEvaluation
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { evaluations ->
            adapter.submitList(evaluations)
        }
        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.EditEvent -> gotoWriteFragment(event.evaluation)
            is Event.DeleteEvent -> showAlertDialog(event.evaluation)
        }
    }

    private fun showAlertDialog(data: MyEvaluation) {
        AlertDialog.Builder(requireContext())
            .setMessage("강의평가를 삭제하면 30P를 잃게 됩니다.\n삭제하시겠습니까?")
            .setNeutralButton("취소") { _, _ -> }
            .setPositiveButton("삭제") { _, _ ->
                viewModel.deletePost(data.id)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

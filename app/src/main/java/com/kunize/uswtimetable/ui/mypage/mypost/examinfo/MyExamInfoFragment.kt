package com.kunize.uswtimetable.ui.mypage.mypost.examinfo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.databinding.FragmentMyExamInfoBinding
import com.kunize.uswtimetable.ui.lecture_info.LectureInfoFragmentDirections
import com.kunize.uswtimetable.ui.mypage.mypost.MyPostResult
import com.kunize.uswtimetable.util.UserPoint
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.kunize.uswtimetable.util.extensions.toast
import com.suwiki.domain.model.LectureExam
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MyExamInfoFragment : Fragment() {
    private var _binding: FragmentMyExamInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MyExamInfoViewModel by viewModels()

    private lateinit var adapter: MyExamInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyExamInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        adapter = MyExamInfoAdapter(viewModel)

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
                    if (viewModel.userInfo.value.point < abs(UserPoint.DELETE_POST)) {
                        this@MyExamInfoFragment.toast("포인트가 부족합니다") // TODO 문자열 추출
                    } else {
                        this@MyExamInfoFragment.toast("삭제할 수 없습니다")
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.myExamInfoContainer
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { myExamInfoList ->
            adapter.submitList(myExamInfoList)
        }

        recyclerView.infiniteScrolls {
            viewModel.scrollBottomEvent()
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.EditEvent -> gotoWriteFragment(event.examInfo)
            is Event.DeleteEvent -> showAlertDialog(event.examInfo)
        }
    }

    private fun showAlertDialog(data: LectureExam) {
        AlertDialog.Builder(requireContext())
            .setMessage("시험정보를 삭제하면 30P를 잃게 됩니다.\n삭제하시겠습니까?") // TODO 문자열 추출
            .setNeutralButton("취소") { _, _ -> }
            .setPositiveButton("삭제") { _, _ ->
                data.id?.let { id ->
                    viewModel.deletePost(id)
                }
            }
            .show()
    }

    private fun gotoWriteFragment(data: LectureExam) {
        val id = data.id ?: return
        val action =
            LectureInfoFragmentDirections.actionGlobalWriteFragment(
                lectureId = id,
                myExamInfo = data,
                isEvaluation = false,
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

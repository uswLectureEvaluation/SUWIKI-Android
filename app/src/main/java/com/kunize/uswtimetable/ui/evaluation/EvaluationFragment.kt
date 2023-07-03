package com.kunize.uswtimetable.ui.evaluation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.SuwikiApplication
import com.kunize.uswtimetable.databinding.FragmentEvaluationBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.FragmentType
import com.kunize.uswtimetable.util.LectureItemViewType
import com.kunize.uswtimetable.util.TextLength.MIN_SEARCH_TEXT_LENGTH
import com.kunize.uswtimetable.util.extensions.repeatOnStarted
import com.mangbaam.presentation.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EvaluationFragment : Fragment() {
    lateinit var binding: FragmentEvaluationBinding
    private val evaluationFooterAdapter = EvaluationFooterAdapter(::moveToDetail)
    private val args: EvaluationFragmentArgs by navArgs()
    private val evaluationViewModel: EvaluationViewModel by viewModels()
    private var imageSortDialog: ImageSortDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        evaluationViewModel.majorType = SuwikiApplication.prefs.getString("openMajorSel", "전체")
        evaluationViewModel.changeType(args.sortType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectedOpenMajor.text = evaluationViewModel.majorType

        binding.recyclerEvaluation.adapter = evaluationFooterAdapter

        binding.viewModel = evaluationViewModel
        binding.lifecycleOwner = this

        binding.btnSearch.setOnClickListener {
            if (isSearchTextLengthNotEnough()) return@setOnClickListener
            goToSearchResult()
        }

        binding.clOpenMajor.setOnClickListener {
            goToSelectOpenMajorFragment()
        }

        // 키보드 검색 클릭 시 프래그먼트 이동 이벤트 구현
        binding.etSearch.setOnEditorActionListener { _, it, _ ->
            var handled = false
            if (it == EditorInfo.IME_ACTION_SEARCH && !isSearchTextLengthNotEnough()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                goToSearchResult(
                    now = EvaluationSortBy.getPositionFromCategory(evaluationViewModel.state.evaluationSortBy),
                    /*now = evaluationViewModel.spinnerTextList.indexOf(
                        evaluationViewModel.sortText.value,
                    ),*/
                )
                handled = true
            }
            handled
        }

        // spinner 설정
        binding.clSort.setOnClickListener {
            imageSortDialog = ImageSortDialog(
                context as AppCompatActivity,
                evaluationViewModel,
            ) // FIXME 강의평가 - 정렬 클릭 튕김
            imageSortDialog?.show()
        }

        evaluationViewModel.dialogItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                imageSortDialog?.dismiss()
            },
        )

        evaluationViewModel.toastViewModel.toastLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                Toast.makeText(
                    requireContext(),
                    evaluationViewModel.toastViewModel.toastMessage,
                    Toast.LENGTH_LONG,
                ).show()
            },
        )
        observe()
    }

    private fun observe() {
        repeatOnStarted {
            evaluationViewModel.stateFlow.collect {
                val recyclerView = binding.recyclerEvaluation
                val items = it.lectureMainsState.items
                if (recyclerView.adapter == null) {
                    recyclerView.adapter = EvaluationFooterAdapter {}
                }
                val evaluationAdapter = recyclerView.adapter as EvaluationFooterAdapter

                val prevItemSize = evaluationAdapter.evaluationListData.size
                val newItemSize = items.size

                evaluationAdapter.evaluationListData = items.toMutableList()

                // api를 호출했을 때 불러올 수 있는 데이터 개수의 최대값이 11이므로 만약 newItemSize의 크기가 11보다 크다면 데이터가 추가되었음을 의미
                when {
                    newItemSize == prevItemSize -> evaluationAdapter.notifyDataSetChanged()
                    newItemSize > 11 -> evaluationAdapter.notifyItemRangeInserted(
                        prevItemSize + 1,
                        newItemSize - prevItemSize + 1,
                    )

                    else -> evaluationAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun isSearchTextLengthNotEnough(): Boolean {
        if (binding.etSearch.text.toString().length < MIN_SEARCH_TEXT_LENGTH) {
            Toast.makeText(requireContext(), "2글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun goToSearchResult(
        msg: String = binding.etSearch.text.toString(),
        now: Int = 0,
    ) {
        val action =
            EvaluationFragmentDirections.actionNavigationEvaluationToSearchResultFragment(msg, now)
        findNavController().navigate(action)
    }

    private fun goToSelectOpenMajorFragment() {
        val action =
            EvaluationFragmentDirections.globalOpenMajor(
                FragmentType.EVALUATION,
                prevSortType = evaluationViewModel.state.evaluationSortBy.labelRes, // evaluationViewModel.spinnerTextList.indexOf(evaluationViewModel.sortText.value),
            )
        findNavController().navigate(action)
    }

    private fun moveToDetail(id: Long) {
        if (id == LectureItemViewType.FOOTER.toLong()) {
            goToSearchResult("", evaluationViewModel.state.selectedIndex)
            return
        }
        if (evaluationViewModel.loggedIn.value) {
            val action = NavGraphDirections.actionGlobalLectureInfoFragment(lectureId = id)
            findNavController().navigate(action)
        } else {
            startActivity<LoginActivity>()
        }
    }
}

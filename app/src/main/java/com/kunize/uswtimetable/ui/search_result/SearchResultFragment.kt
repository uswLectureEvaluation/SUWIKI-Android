package com.kunize.uswtimetable.ui.search_result

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.kunize.uswtimetable.databinding.FragmentSearchResultBinding
import com.kunize.uswtimetable.ui.common.EventObserver
import com.kunize.uswtimetable.ui.evaluation.EvaluationFooterAdapter
import com.kunize.uswtimetable.ui.login.LoginActivity
import com.kunize.uswtimetable.util.FragmentType
import com.kunize.uswtimetable.util.TextLength.MIN_SEARCH_TEXT_LENGTH
import com.kunize.uswtimetable.util.extensions.infiniteScrolls
import com.mangbaam.presentation.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding = _binding ?: error("binding is null")

    private val searchResultAdapter = SearchResultAdapter { id ->
        if (searchResultViewModel.isLoggedIn.value) {
            val action = NavGraphDirections.actionGlobalLectureInfoFragment(lectureId = id)
            findNavController().navigate(action)
        } else {
            startActivity<LoginActivity>()
        }
    }
    private val searchResultViewModel: SearchResultViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()
    private var sortDialog: SortDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchResultViewModel.initType()
        searchResultViewModel.majorType = SuwikiApplication.prefs.getString("openMajorSel", "전체")
        if (args.searchLectureName.isBlank()) {
            searchResultViewModel.changeType(args.sortType)
        } else {
            searchResultViewModel.search(args.searchLectureName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = searchResultViewModel
        binding.lifecycleOwner = this

        binding.tvSelectedOpenMajor.text = searchResultViewModel.majorType
        binding.clOpenMajor.setOnClickListener {
            val action =
                SearchResultFragmentDirections.globalOpenMajor(
                    FragmentType.SEARCH_RESULT,
                    searchResultViewModel.searchValue,
                    searchResultViewModel.spinnerTextList.indexOf(searchResultViewModel.sortText.value),
                )
            findNavController().navigate(action)
        }

        binding.recyclerSearchResult.adapter = searchResultAdapter

        binding.recyclerSearchResult.infiniteScrolls {
            searchResultViewModel.scrollBottomEvent()
        }

        binding.clSort.setOnClickListener {
            sortDialog = SortDialog(
                context as AppCompatActivity,
                searchResultViewModel,
                searchResultViewModel.spinnerTextList,
            )
            sortDialog?.show()
        }

        searchResultViewModel.dialogItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                sortDialog?.dismiss()
            },
        )

        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.apply {
            setText(args.searchLectureName)
            setSelection(args.searchLectureName.length)
        }

        binding.btnSearch.setOnClickListener {
            if (isSearchTextLengthNotEnough()) return@setOnClickListener
            searchResultViewModel.search(binding.etSearch.text.toString())
        }

        binding.etSearch.setOnEditorActionListener { _, it, _ ->
            var handled = false
            if (it == EditorInfo.IME_ACTION_SEARCH && !isSearchTextLengthNotEnough()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                handled = true
                searchResultViewModel.search(binding.etSearch.text.toString())
            }
            handled
        }

        searchResultViewModel.toastViewModel.toastLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                Toast.makeText(
                    requireContext(),
                    searchResultViewModel.toastViewModel.toastMessage,
                    Toast.LENGTH_LONG,
                ).show()
            },
        )

        searchResultViewModel.commonRecyclerViewViewModel.itemList.observe(viewLifecycleOwner) {
            val recyclerView = binding.recyclerSearchResult
            val items = it
            if (recyclerView.adapter == null) {
                recyclerView.adapter = EvaluationFooterAdapter { }
            }
            val evaluationAdapter = recyclerView.adapter as SearchResultAdapter

            val prevItemSize = evaluationAdapter.evaluationListData.size
            val newItemSize = items.size

            Log.d("Scroll", "$prevItemSize $newItemSize")
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

    private fun isSearchTextLengthNotEnough(): Boolean {
        if (binding.etSearch.text.toString().length < MIN_SEARCH_TEXT_LENGTH) {
            Toast.makeText(requireContext(), "2글자 이상 입력해주세요!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

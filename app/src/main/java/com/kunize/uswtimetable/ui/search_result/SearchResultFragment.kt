package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.databinding.FragmentSearchResultBinding
import com.kunize.uswtimetable.dataclass.LectureItemViewType
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel

class SearchResultFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentSearchResultBinding

    //EvaluationFragment와 동일한 viewModel 사용
    private lateinit var viewModel: EvaluationViewModel
    private lateinit var sortBtn: MutableList<RadioButton>
    var prevSelRadioButton: RadioButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[EvaluationViewModel::class.java]
        with(binding) {
            sortBtn = mutableListOf(satisfactionBtn, honeyBtn, learningBtn, dateBtn, totalBtn)
        }

        //TODO 검색 기능 추가

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args: SearchResultFragmentArgs by navArgs()
        val msg = args.searchLectureName

        viewModel.setViewType(LectureItemViewType.SHORT)

        viewModel.addData(ArrayList(dummyData.subList(0, 10)))

        binding.recyclerSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1
                Log.d("Scroll","$lastVisibleItemPosition , $itemTotalCount")
                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    //로딩 바 제거, 서버 연동 시 새로운 데이터를 받아 온 후에 제거
                    viewModel.deleteLoading()
                    //스크롤 끝에 도달한 경우 새로운 데이터를 받아옴
                    val newData = dummyData.subList(0, 0)
                    viewModel.addData(ArrayList(newData))
                }
            }
        })

        binding.searchLecture.apply {
            setText(msg)
            setSelection(msg.length)
        }

        sortBtn.forEach { btn ->
            btn.setOnClickListener(this)
        }

        return binding.root
    }

    override fun onClick(v: View?) {
        val radioBtn = v as RadioButton
        sortBtn.forEach { btn ->
            btn.text = btn.text.toString().split(" ")[0]
        }
        if(prevSelRadioButton == radioBtn) {
            binding.radioGroup.clearCheck()
            prevSelRadioButton = null
            return
        }
        prevSelRadioButton = radioBtn
        //TODO 클릭 될 때마다 새로운 데이터를 서버에서 받아오는 기능 추가
        viewModel.changeData(ArrayList(dummyData.subList(3, 5)))
        radioBtn.text = radioBtn.text.toString() + " ↑"
    }
}
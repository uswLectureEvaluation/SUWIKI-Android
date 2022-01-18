package com.kunize.uswtimetable.ui.search_result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.adapter.EvaluationListAdapter
import com.kunize.uswtimetable.databinding.FragmentSearchResultBinding
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.dataclass.LectureItemViewType
import com.kunize.uswtimetable.ui.evaluation.EvaluationFragment.Companion.dummyData
import com.kunize.uswtimetable.ui.evaluation.EvaluationViewModel
import com.kunize.uswtimetable.util.infiniteScrolls
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class SearchResultFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentSearchResultBinding

    //EvaluationFragment와 동일한 viewModel 사용
    private lateinit var viewModel: EvaluationViewModel
    private lateinit var sortBtn: MutableList<RadioButton>
    val args: SearchResultFragmentArgs by navArgs()

    var msg = ""
    var prevSel = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //백스택을 통해 프래그먼트가 다시 실행되면 onCreateView()가 호출됨
        //만약 아래 코드가 onCreateView()에 있는 경우 EvaluationFragment에서 가져온 args.sortType으로 라디오 버튼이 선택됨
        /*
        ex)
        1. EvaluationFragment에서 "만족도" 스피너가 선택된 상태로 SearchResult 프래그먼트 실행
        2. SearchResult에서 "만족도" 라디오 버튼이 선택됨
        3. 사용자가 "날짜" 라디오 버튼 선택 후 LectureInfoFragment로 이동
        4. LectureInfoFragment에서 백스택을 통해 SearchResult로 돌아온 경우
        5. 아래 코드가 onCreateView()에 있다면 다시 "만족도"가 선택됨
         */

        msg = args.searchLectureName
        prevSel = args.sortType
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result,container, false)
        viewModel = ViewModelProvider(this)[EvaluationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setViewType(LectureItemViewType.SHORT)

        binding.recyclerSearchResult.infiniteScrolls {
            CoroutineScope(Main).launch {
                delay(1000)
                //로딩 바 제거, 서버 연동 시 새로운 데이터를 받아 온 후에 제거
                viewModel.deleteLoading()
                //스크롤 끝에 도달한 경우 새로운 데이터를 받아옴
                val newData = dummyData.subList(0, 0)
                viewModel.addData(ArrayList(newData))
            }
        }

        with(binding) {
            sortBtn = mutableListOf(dateBtn, honeyBtn, satisfactionBtn , learningBtn, totalBtn)
        }

        sortBtn.forEach { btn ->
            btn.setOnClickListener(this)
        }


        binding.searchLecture.apply {
            if(prevSel >= 0) {
                setText(msg)
                setSelection(msg.length)
                //TODO 검색 결과에 맞는 데이터 받아온 후, changeData 수행
                viewModel.changeData(ArrayList(dummyData.subList(0, 0)))
            }
        }

        //TODO 검색 기능 추가

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sortBtn.forEach { btn ->
            if(prevSel == -1 && btn.isChecked) {
                btn.text = btn.text.toString() + " ↑"
            }
        }

        if(prevSel >= 0) {
            sortBtn[prevSel].performClick()
            prevSel = -1
        }
    }

    override fun onClick(v: View?) {
        val radioBtn = v as RadioButton
        sortBtn.forEach { btn ->
            btn.text = btn.text.toString().split(" ")[0]
        }
        //TODO 클릭 될 때마다 새로운 데이터를 서버에서 받아오는 기능 추가
        val a = Random().nextInt(2)
        val b = Random().nextInt(6) + 2
        val temp = ArrayList<EvaluationData?>()
        temp.add(null)
        viewModel.changeData(temp)
        radioBtn.text = radioBtn.text.toString() + " ↑"
    }
}
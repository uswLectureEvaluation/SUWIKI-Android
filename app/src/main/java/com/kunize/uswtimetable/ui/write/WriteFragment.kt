package com.kunize.uswtimetable.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentWriteBinding
import com.kunize.uswtimetable.util.seekbarChangeListener

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding

    private lateinit var writeViewModel : WriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        writeViewModel = ViewModelProvider(this)[WriteViewModel::class.java]
        binding.viewModel = writeViewModel
        binding.lifecycleOwner = this

        val args: WriteFragmentArgs by navArgs()
        binding.writeLectureName.text = args.lectureName
        binding.writeProfessor.text = args.professor

        //TODO 수강학기 선택 동적으로 불러와서 처리하기

        binding.testGroup.visibility = View.GONE
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.writeFragment, true)
        }

        binding.finishButton.setOnClickListener {
            goToLectureInfoFragment()
        }


        binding.honeySeekBar.seekbarChangeListener {
            writeViewModel.changeHoneyScore(it)
        }

        binding.satisfactionSeekBar.seekbarChangeListener {
            writeViewModel.changeSatisfactionScore(it)
        }

        binding.learningSeekBar.seekbarChangeListener {
            writeViewModel.changeLearningScore(it)
        }

        /*
        * progress 초기화를 하지 않을 경우
        * 강의평가 쓰기 -> 꿀강 지수 2, 배움 지수 4로 설정 -> 완료
        * 위 과정 진행 후 다시 강의평가 쓰기를 클릭할 경우
        * 꿀강 지수 및 배움 지수가 0으로 초기화 되지 않고 2와 4로 남아있음
        * AVD 및 V20에서는 해당 문제가 발생하지만 갤럭시 s10e 에서는 발생하지 않음
        *
        * 해당 문제를 해결하기 위해 progress 초기화
        * */
        binding.satisfactionSeekBar.progress = 6
        binding.learningSeekBar.progress = 6
        binding.honeySeekBar.progress = 6

        return binding.root
    }

    private fun goToLectureInfoFragment() {
        val action =
            WriteFragmentDirections.actionWriteFragmentToLectureInfoFragment(binding.writeLectureName.text.toString(), binding.writeProfessor.text.toString())
        findNavController().navigate(action)
    }
}
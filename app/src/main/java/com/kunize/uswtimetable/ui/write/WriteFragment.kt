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

        return binding.root
    }

    private fun goToLectureInfoFragment() {
        val action =
            WriteFragmentDirections.actionWriteFragmentToLectureInfoFragment(binding.writeLectureName.text.toString(), binding.writeProfessor.text.toString())
        findNavController().navigate(action)
    }
}
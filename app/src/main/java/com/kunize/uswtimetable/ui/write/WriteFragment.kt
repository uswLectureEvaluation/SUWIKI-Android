package com.kunize.uswtimetable.ui.write

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kunize.uswtimetable.NavGraphDirections
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.databinding.FragmentWriteBinding
import com.kunize.uswtimetable.dataclass.LectureProfessorName
import com.kunize.uswtimetable.dataclass.MyEvaluation
import com.kunize.uswtimetable.dataclass.MyExamInfo
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.WriteFragmentTitle
import com.kunize.uswtimetable.util.seekbarChangeListener
import kotlin.math.roundToInt

class WriteFragment : Fragment() {

    lateinit var binding: FragmentWriteBinding

    private lateinit var writeViewModel: WriteViewModel

    private lateinit var taskRadioBtnList: List<RadioButton>
    private lateinit var gradeRadioBtnList: List<RadioButton>

    private lateinit var testContentCheckBoxList: List<CheckBox>
    private lateinit var difficultyRadioBtnList: List<RadioButton>

    private var defaultHoneyProgress = 6
    private var defaultLearningProgress = 6
    private var defaultSatisfactionProgress = 6

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        writeViewModel = ViewModelProvider(this)[WriteViewModel::class.java]
        binding.viewModel = writeViewModel
        binding.lifecycleOwner = this

        val args: WriteFragmentArgs by navArgs()

        setInitValueWhenWrite(args)
        setFragmentViewType(args)
        setInitValueWhenEditMyEvaluation(args)
        setInitValueWhenEditMyExam(args)
        setSeekBarListener()
        setSeekBarProgress()

        //TODO 수강학기 선택 동적으로 불러와서 처리하기


        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack(R.id.writeFragment, true)
        }

        binding.finishButton.setOnClickListener {
            if (args.myExamInfo == null && args.myEvaluation == null)
                goToLectureInfoFragment()
            else
                goToMyPostFragment()
        }

        return binding.root
    }

    private fun setSeekBarListener() {
        binding.honeySeekBar.seekbarChangeListener {
            writeViewModel.changeHoneyScore(it)
        }

        binding.satisfactionSeekBar.seekbarChangeListener {
            writeViewModel.changeSatisfactionScore(it)
        }

        binding.learningSeekBar.seekbarChangeListener {
            writeViewModel.changeLearningScore(it)
        }
    }

    private fun setInitValueWhenEditMyExam(args: WriteFragmentArgs) {
        args.myExamInfo?.let {
            setLectureProfessorName(it)
            setTestContentCheckBox(it)
            setDifficultyRadioBtn(it)
            binding.writeContent.setText(it.content)
            binding.writeType.text = WriteFragmentTitle.EDIT_MY_EXAM
        }
    }

    private fun setDifficultyRadioBtn(it: MyExamInfo) {
        difficultyRadioBtnList = listOf(
            binding.easyRadioButton,
            binding.veryEasyRadioButton,
            binding.normalRadioButton,
            binding.difficultRadioButton,
            binding.veryDifficultRadioButton
        )
        for (radioButton in difficultyRadioBtnList) {
            Log.d("radioBtn", "${it.examDifficulty}, ${radioButton.text.toString()}")
            if (it.examDifficulty == radioButton.text.toString()) {
                radioButton.isChecked = true
                break
            }
        }
    }

    private fun setTestContentCheckBox(it: MyExamInfo) {
        testContentCheckBoxList = listOf(
            binding.genealogyCheckBox,
            binding.textbookCheckBox,
            binding.pptCheckBox,
            binding.noteCheckBox,
            binding.applicationCheckBox,
            binding.practiceCheckBox,
            binding.taskCheckBox
        )
        for (checkBox in testContentCheckBoxList) {
            for (dataString in it.evaluationType.split(",")) {
                if (checkBox.text == dataString)
                    checkBox.isChecked = true
            }
        }
    }

    private fun setLectureProfessorName(it: MyExamInfo) {
        binding.writeLectureName.text = it.subject
        binding.writeProfessor.text = it.professor
    }

    private fun setFragmentViewType(args: WriteFragmentArgs) {
        if (args.isEvaluation) {
            binding.testGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EVALUATION
        } else {
            binding.lectureGroup.visibility = View.GONE
            binding.writeType.text = WriteFragmentTitle.WRITE_EXAM
        }
    }

    private fun setInitValueWhenWrite(args: WriteFragmentArgs) {
        args.lectureProfessorName?.let {
            binding.writeLectureName.text = it.subject
            binding.writeProfessor.text = it.professor
        }
    }

    private fun setInitValueWhenEditMyEvaluation(args: WriteFragmentArgs) {
        args.myEvaluation?.let {
            setLectureProfessorName(it)
            setDefaultSeekBarProgressValue(it)
            setTeamRadioBtn(it)
            setTaskRadioBtn(it)
            setGradeRadioBtn(it)
            binding.writeContent.setText(it.content)
            binding.writeType.text = WriteFragmentTitle.EDIT_MY_EVALUATION
        }
    }

    private fun setGradeRadioBtn(it: MyEvaluation) {
        gradeRadioBtnList = listOf(
            binding.gradeNormalRadioButton,
            binding.gradeGoodRadioButton,
            binding.gradeDifficultRadioButton
        )
        for (taskBtn in gradeRadioBtnList) {
            if (it.grade == taskBtn.text.toString()) {
                taskBtn.isChecked = true
                break
            }
        }
    }

    private fun setTaskRadioBtn(it: MyEvaluation) {
        taskRadioBtnList = listOf(
            binding.taskNotExistRadioButton,
            binding.taskNormalRadioButton,
            binding.taskManyRadioButton
        )
        for (taskBtn in taskRadioBtnList) {
            if (it.homework == taskBtn.text.toString()) {
                taskBtn.isChecked = true
                break
            }
        }
    }

    private fun setTeamRadioBtn(it: MyEvaluation) {
        if (it.team)
            binding.teamExistRadioButton.isChecked = true
        else
            binding.teamNotExistRadioButton.isChecked = false
    }

    private fun setDefaultSeekBarProgressValue(it: MyEvaluation) {
        defaultHoneyProgress = (it.honey * 2).roundToInt()
        defaultLearningProgress = (it.learning * 2).roundToInt()
        defaultSatisfactionProgress = (it.satisfaction * 2).roundToInt()
    }

    private fun setLectureProfessorName(it: MyEvaluation) {
        binding.writeLectureName.text = it.subject
        binding.writeProfessor.text = it.professor
    }

    private fun setSeekBarProgress() {
        /*
        * progress 초기화를 하지 않을 경우
        * 강의평가 쓰기 -> 꿀강 지수 2, 배움 지수 4로 설정 -> 완료
        * 위 과정 진행 후 다시 강의평가 쓰기를 클릭할 경우
        * 꿀강 지수 및 배움 지수가 0으로 초기화 되지 않고 2와 4로 남아있음
        * AVD 및 V20에서는 해당 문제가 발생하지만 갤럭시 s10e 에서는 발생하지 않음
        *
        * 해당 문제를 해결하기 위해 progress 초기화
        * */
        binding.satisfactionSeekBar.progress = defaultHoneyProgress
        binding.learningSeekBar.progress = defaultLearningProgress
        binding.honeySeekBar.progress = defaultSatisfactionProgress
    }

    private fun goToLectureInfoFragment() {
        val action = NavGraphDirections.actionGlobalLectureInfoFragment(
            lectureProfessorName =
            LectureProfessorName(
                binding.writeLectureName.text.toString(),
                binding.writeProfessor.text.toString()
            )
        )
        findNavController().navigate(action)
    }

    private fun goToMyPostFragment() {
        val action = NavGraphDirections.actionGlobalMyPostFragment()
        findNavController().navigate(action)
    }
}
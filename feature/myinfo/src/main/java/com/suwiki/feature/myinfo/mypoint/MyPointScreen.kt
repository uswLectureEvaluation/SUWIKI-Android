package com.suwiki.feature.myinfo.mypoint

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

const val WRITE_LECTURE_EVALUATION_POINT = 10
const val WRITE_EXAM_EVALUATION_POINT = 20
const val VIEW_EXAM_POINT = 20

@Composable
fun MyPointRoute(
  viewModel: MyPointViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val scrollState = rememberScrollState()
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is MyPointSideEffect.PopBackStack -> popBackStack()
      is MyPointSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  MyPointScreen(
    uiState = uiState,
    scrollState = scrollState,
    popBackStack = viewModel::popBackStack,
  )
}

@Composable
fun MyPointScreen(
  uiState: MyPointState = MyPointState(),
  scrollState: ScrollState,
  popBackStack: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_info_point),
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = popBackStack,
    )
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(start = 24.dp, end = 24.dp)
        .verticalScroll(scrollState),
    ) {
      TitleContainer(title = stringResource(R.string.word_point_status))
      MyPointRowContainer(
        modifier = Modifier.padding(vertical = 14.dp),
        title = stringResource(R.string.word_current_point),
        point = stringResource(R.string.word_current_point_value, uiState.currentPoint),
        verticalAlignment = Alignment.Bottom,
      )
      MyPointRowContainer(
        modifier = Modifier.padding(vertical = 14.dp),
        title = stringResource(R.string.text_written_lecture_evaluation, uiState.writtenLectureEvaluations),
        point = stringResource(R.string.word_plus_point_value, uiState.writtenLectureEvaluations * WRITE_LECTURE_EVALUATION_POINT),
        verticalAlignment = Alignment.Bottom,
      )
      MyPointRowContainer(
        modifier = Modifier.padding(vertical = 14.dp),
        title = stringResource(R.string.text_written_exam_evaluation, uiState.writtenExamEvaluations * WRITE_EXAM_EVALUATION_POINT),
        point = stringResource(R.string.word_plus_point_value, uiState.writtenExamEvaluations * WRITE_EXAM_EVALUATION_POINT),
        verticalAlignment = Alignment.Bottom,
      )
      MyPointRowContainer(
        modifier = Modifier.padding(vertical = 14.dp),
        title = stringResource(R.string.text_view_exam, uiState.viewExam),
        point = stringResource(R.string.word_minus_point_value, uiState.viewExam * VIEW_EXAM_POINT),
        verticalAlignment = Alignment.Bottom,
      )
      Spacer(modifier = Modifier.height(12.dp))
      PointPolicyContainer()
      Spacer(modifier = Modifier.height(24.dp))
      if (!uiState.purchaseHistory.isEmpty()) {
        TitleContainer(title = stringResource(R.string.word_point_use_history))
      }
      Column {
        uiState.purchaseHistory.forEach { purchaseHistory ->
          Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 17.dp),
          ) {
            Text(
              modifier = Modifier.weight(1f),
              maxLines = 1,
              text = purchaseHistory.createDate.toString(),
              style = SuwikiTheme.typography.body5,
              color = Gray95,
            )
            Text(
              modifier = Modifier.weight(1f),
              maxLines = 1,
              textAlign = TextAlign.End,
              text = purchaseHistory.lectureName,
              style = SuwikiTheme.typography.body5,
            )
          }
        }
      }
    }
  }
}

@Composable
fun TitleContainer(
  title: String,
) {
  Text(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 14.dp),
    text = title,
    style = SuwikiTheme.typography.body2,
  )
}

@Composable
fun MyPointRowContainer(
  modifier: Modifier = Modifier,
  title: String,
  point: String,
  verticalAlignment: Alignment.Vertical,
) {
  Column {
    Row(
      verticalAlignment = verticalAlignment,
      horizontalArrangement = Arrangement.spacedBy(48.dp),
      modifier = modifier.fillMaxWidth(),
    ) {
      Text(
        modifier = Modifier.width(120.dp),
        text = title,
        style = SuwikiTheme.typography.body5,
        color = Gray95,
      )
      Text(
        text = point,
        style = SuwikiTheme.typography.body5,
      )
    }
    HorizontalDivider(color = GrayF6)
  }
}

@Composable
fun PointPolicyText(
  text: String,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Text(
      text = stringResource(R.string.word_dot),
      style = SuwikiTheme.typography.caption4,
    )
    Text(
      text = text,
      style = SuwikiTheme.typography.caption4,
    )
  }
}

@Composable
fun PointPolicyContainer() {
  Column(
    modifier = Modifier
      .padding(vertical = 12.dp),
  ) {
    PointPolicyText(stringResource(R.string.text_point_policy_first))
    PointPolicyText(stringResource(R.string.text_point_policy_second))
    PointPolicyText(stringResource(R.string.text_point_policy_third))
    PointPolicyText(stringResource(R.string.text_point_policy_fourth))
    Text("")
    PointPolicyText(stringResource(R.string.text_point_policy_fifth))
    PointPolicyText(stringResource(R.string.text_point_policy_sixth))
    PointPolicyText(stringResource(R.string.text_point_policy_seventh))
    Text("")
    PointPolicyText(stringResource(R.string.text_point_policy_eighth))
    PointPolicyText(stringResource(R.string.text_point_policy_ninth))
  }
}

@Preview
@Composable
fun MyPointScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    MyPointScreen(scrollState = scrollState)
  }
}

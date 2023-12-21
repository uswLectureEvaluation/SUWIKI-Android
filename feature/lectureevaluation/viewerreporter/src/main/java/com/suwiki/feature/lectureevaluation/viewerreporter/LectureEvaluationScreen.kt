package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun LectureEvaluationScreen(
  padding: PaddingValues,
) {
  Text(text = "강의평가")
}

@Preview
@Composable
fun LectureEvaluationScreenPreview() {
  SuwikiTheme {
    LectureEvaluationScreen(padding = PaddingValues(0.dp))
  }
}

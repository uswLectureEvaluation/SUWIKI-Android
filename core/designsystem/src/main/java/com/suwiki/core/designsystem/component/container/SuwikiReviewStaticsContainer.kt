package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.badge.SuwikiBasicLabel
import com.suwiki.core.designsystem.component.badge.SuwikiChipType
import com.suwiki.core.designsystem.component.badge.SuwikiColorBadge
import com.suwiki.core.designsystem.component.card.SuwikiReviewGradeCard
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiReviewStaticsContainer(
  modifier: Modifier = Modifier,
  typeLessonText: String,
  titleText: String,
  departmentOfferingText: String,
  professorNameText: String,
  reviewCount: Int,
  ratingCount: Float,
  honeyQualityCount: Float,
  learningQualityCount: Float,
  satisfactionCount: Float,
  onClick: () -> Unit,
  isChecked: Boolean,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        SuwikiBasicLabel(
          text = typeLessonText,
          textColor = Gray6A,
          backgroundColor = GrayF6,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = titleText,
          style = SuwikiTheme.typography.header3,
          color = Black,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .wrapContentWidth()
            .height(IntrinsicSize.Min),
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          Text(
            text = departmentOfferingText,
            style = SuwikiTheme.typography.body6,
            color = Gray6A,
          )
          VerticalDivider(
            color = GrayDA,
            modifier = Modifier
              .fillMaxHeight()
              .width(1.dp)
              .padding(vertical = 3.dp),
          )
          Text(
            text = professorNameText,
            style = SuwikiTheme.typography.body6,
            color = Gray6A,
          )
        }
        Spacer(modifier = Modifier.height(14.dp))
        if (isChecked) {
          Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp),
          ) {
            SuwikiColorBadge(
              type = SuwikiChipType.GREEN,
              text = "학점",
              englishText = "label",
            )
            SuwikiColorBadge(
              type = SuwikiChipType.BLUE,
              text = "과제",
              englishText = "label",
            )
            SuwikiColorBadge(
              type = SuwikiChipType.ORANGE,
              text = "팀지",
              englishText = "label",
            )
          }
        }
        Spacer(modifier = Modifier.height(23.dp))
        SuwikiReviewGradeCard(
          reviewCount = reviewCount,
          ratingCount = ratingCount,
          honeyQualityCount = honeyQualityCount,
          learningQualityCount = learningQualityCount,
          satisfactionCount = satisfactionCount,
          onClick = onClick,
        )
      }
    }
  }
}

@Preview
@Composable
fun SuwikiReviewStaticsContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiReviewStaticsContainer(
        typeLessonText = "강의유형",
        titleText = "Tilte",
        departmentOfferingText = "개설학과",
        professorNameText = "교수명",
        reviewCount = 3,
        ratingCount = 3.0f,
        honeyQualityCount = 3.0f,
        learningQualityCount = 3.0f,
        satisfactionCount = 3.0f,
        onClick = { /*TODO*/ },
        isChecked = true,
      )
      SuwikiReviewStaticsContainer(
        typeLessonText = "강의유형",
        titleText = "Tilte",
        departmentOfferingText = "개설학과",
        professorNameText = "교수명",
        reviewCount = 0,
        ratingCount = 0.0f,
        honeyQualityCount = 0.0f,
        learningQualityCount = 0.0f,
        satisfactionCount = 0.0f,
        onClick = { /*TODO*/ },
        isChecked = false,
      )
    }
  }
}

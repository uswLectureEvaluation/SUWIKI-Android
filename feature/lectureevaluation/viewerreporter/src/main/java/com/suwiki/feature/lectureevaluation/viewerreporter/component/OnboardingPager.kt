package com.suwiki.feature.lectureevaluation.viewerreporter.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.feature.lectureevaluation.viewerreporter.R

const val ONBOARDING_PAGE_COUNT = 2

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnboardingPager(pagerState: PagerState) {
  HorizontalPager(
    state = pagerState,
  ) { page ->
    when (page) {
      0 -> {
        OnboardingPageContent(
          title = stringResource(R.string.onboarding_title),
          description = stringResource(R.string.onboarding1_description),
          icon = R.drawable.ic_onboarding1,
        )
      }

      1 -> {
        OnboardingPageContent(
          title = stringResource(R.string.onboarding_title),
          description = stringResource(R.string.onboarding2_description),
          icon = R.drawable.ic_onboarding2,
        )
      }
    }
  }
}

@Composable
private fun OnboardingPageContent(
  title: String,
  description: String,
  @DrawableRes icon: Int,
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      modifier = Modifier.size(160.dp),
      painter = painterResource(id = icon),
      contentDescription = "",
    )

    Spacer(modifier = Modifier.size(42.dp))

    Text(text = title, style = SuwikiTheme.typography.header2, textAlign = TextAlign.Center)

    Spacer(modifier = Modifier.size(7.dp))

    Text(text = description, style = SuwikiTheme.typography.body5, color = Gray6A)
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPagerIndicator(
  pageCount: Int,
  pagerState: PagerState,
) {
  Row(
    horizontalArrangement = Arrangement.Center,
  ) {
    repeat(pageCount) { iteration ->
      val color = if (pagerState.currentPage == iteration) Primary else GrayDA
      Box(
        modifier = Modifier
          .padding(horizontal = 4.dp)
          .clip(CircleShape)
          .background(color)
          .size(5.dp),
      )
    }
  }
}

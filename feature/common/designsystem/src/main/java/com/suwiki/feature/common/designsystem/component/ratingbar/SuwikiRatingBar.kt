package com.suwiki.feature.common.designsystem.component.ratingbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.suwiki.feature.common.designsystem.R

@Composable
fun SuwikiRatingBar(
  modifier: Modifier = Modifier,
  rating: Float,
) {
  RatingBar(
    modifier = modifier,
    value = rating,
    size = 12.dp,
    spaceBetween = 0.dp,
    painterEmpty = painterResource(id = R.drawable.ic_star_empty),
    painterFilled = painterResource(id = R.drawable.ic_star_filled),
    onValueChange = {},
    onRatingChanged = {},
  )
}

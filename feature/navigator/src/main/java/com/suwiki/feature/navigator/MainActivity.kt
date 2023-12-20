package com.suwiki.feature.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.core.designsystem.theme.SuwikiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SuwikiTheme {
        Greeting(name = "Suwiki")
      }
    }
  }
}

@Composable
fun Greeting(
  name: String,
) {
  Text(
    text = "Hello $name!",
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  SuwikiTheme {
    Greeting(name = "Suwiki")
  }
}

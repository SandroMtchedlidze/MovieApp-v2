package com.space.movieapp

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.space.core_ui.R
import com.space.ui.component.NavButton
import com.space.ui.component.SearchField
import com.space.ui.theme.Color
import com.space.ui.theme.MovieAppTheme
import com.space.ui.theme.Spacing


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ggggg",
                            style = MovieAppTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,

                            )
                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                            text = "ggggg",
                            style = MovieAppTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        NavButton(
                            false, "Home", painterResource(R.drawable.home), {},
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            SearchField(
                                "",
                                {},
                                {},
                                {})

                            Spacer(Modifier.height(20.dp))
                            SearchField(
                                "asfasf",
                                {},
                                {},
                                {})
                        }
                    }
                }
            }
        }


    }
}



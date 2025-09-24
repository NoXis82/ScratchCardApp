package ru.noxis.scratchcardapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.noxis.lib.compose.scratchcard.ScratchcardBox
import ru.noxis.lib.compose.scratchcard.rememberScratchcardState
import ru.noxis.scratchcardapp.ui.theme.ScratchCardAppTheme

class Sample: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScratchCardAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContentView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun ContentView(modifier: Modifier = Modifier) {
    val state = rememberScratchcardState(
        onScratchStart = {
            logMsg { "onScratchStart" }
            true
        },
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Button(onClick = {
            // Clear the overlay
            state.clear()
        }) {
            Text(text = "Clear")
        }

        Button(onClick = {
            // Reset and display the overlay
            state.reset()
        }) {
            Text(text = "Reset")
        }

        ScratchcardBox(
            state = state,
            overlay = {
                // The overlay
                Box(Modifier.background(Color.Gray))
            },
            content = {
                // The content
                Image(
                    painter = painterResource(R.drawable.scratchcard_content),
                    contentDescription = null,
                )
            },
        )
    }
}
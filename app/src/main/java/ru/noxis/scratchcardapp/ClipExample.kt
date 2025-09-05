package ru.noxis.scratchcardapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ClipExample(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .size(200.dp)
                .border(2.dp, Color.Black)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                    translationY = 50.dp.toPx()
                }
                .background(Color(0xFFF06292))
        ) {
            Text(
                "Hello Compose",
                style = TextStyle(color = Color.Black, fontSize = 46.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0xFF4DB6AC))
        )
    }
}
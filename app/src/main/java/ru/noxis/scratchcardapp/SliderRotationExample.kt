package ru.noxis.scratchcardapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SliderRotationExample(modifier: Modifier = Modifier) {
    var rotationX by remember { mutableFloatStateOf(360f) }
    var rotationY by remember { mutableFloatStateOf(360f) }
    var rotationZ by remember { mutableFloatStateOf(360f) }
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ScaleX")
        Slider(
            value = rotationX,
            valueRange = (0f..360f),
            onValueChange = { rotationX = it }
        )
        Spacer(Modifier.height(16.dp))
        Text("ScaleY")
        Slider(
            value = rotationY,
            valueRange = (0f..360f),
            onValueChange = { rotationY = it }
        )
        Spacer(Modifier.height(16.dp))
        Slider(
            value = rotationZ,
            valueRange = (0f..360f),
            onValueChange = { rotationZ = it }
        )


        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin(0f, 0f)
                    this.rotationX = rotationX
                    this.rotationY = rotationY
                    this.rotationZ = rotationZ
                }
        )

    }
}
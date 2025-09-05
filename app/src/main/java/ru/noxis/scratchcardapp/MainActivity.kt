package ru.noxis.scratchcardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.noxis.scratchcardapp.ui.theme.ScratchCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScratchCardAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        "Hello Compose!",
        modifier = Modifier
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E82F0),
                        Color(0xFF42A5F5)
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
            }
    )

}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        "Hello Compose!",
//        modifier = Modifier
//            .drawBehind {
//                drawRoundRect(
//                    Color(0xFFBBAAEE),
//                    cornerRadius = CornerRadius(10.dp.toPx())
//                )
//            }
//            .padding(4.dp)
//    )
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//
//    var pointerOffset by remember {
//        mutableStateOf(Offset(0f, 0f))
//    }
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxSize()
//            .pointerInput("dragging") {
//                detectDragGestures { change, dragAmount ->
//                    pointerOffset += dragAmount
//                }
//            }
//            .onSizeChanged {
//                pointerOffset = Offset(it.width / 2f, it.height / 2f)
//            }
//            .drawWithContent {
//                drawContent()
//                // рисуем полностью чёрную область с маленькой замочной скважиной
//                // в pointerOffset, через которую видна часть пользовательского интерфейса.
//                drawRect(
//                    Brush.radialGradient(
//                        listOf(Color.Transparent, Color.Black),
//                        center = pointerOffset,
//                        radius = 100.dp.toPx(),
//                    )
//                )
//            }
//    ) {
//        Text(
//            text = "Hello $name!"
//        )
//    }
//
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScratchCardAppTheme {
        Greeting("Android")
    }
}
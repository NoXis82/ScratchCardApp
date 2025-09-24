package ru.noxis.lib.compose.scratchcard

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent

private fun Modifier.scratchcard(
    state: ScratchcardState,
): Modifier {
    return composed {
        if (state.forceRecomposition) {

            state.redraw
        }

        drawWithContent {

            state.reportDraw()
        }

    }
}
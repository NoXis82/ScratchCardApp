@file:OptIn(FlowPreview::class)

package ru.noxis.lib.compose.scratchcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun rememberScratchcardState(
    /** Return the threshold for clearing the overlay. */
    getClearThreshold: () -> Float = { 0.5f },
    /** It is called when the scratch starts. Return true to continue. */
    onScratchStart: () -> Boolean = { true },
): ScratchcardState {
    return rememberSaveable(saver = ScratchcardState.Saver) {
        ScratchcardState()
    }.apply {
        this.coroutineScope = rememberCoroutineScope()
        this.graphicsLayer = rememberGraphicsLayer()
        this.getClearThreshold = getClearThreshold
        this.onScratchStart = onScratchStart
    }
}


/**
 *
 */
class ScratchcardState internal constructor(
    initialCleared: Boolean = false
) {

    internal val path = Path()
    private var _calculateJob: Job? = null
    internal lateinit var coroutineScope: CoroutineScope
    internal lateinit var graphicsLayer: GraphicsLayer
    internal lateinit var getClearThreshold: () -> Float
    internal lateinit var onScratchStart: () -> Boolean

    internal var redraw by mutableIntStateOf(0)
        private set

    private var _forceRecompositionJob: Job? = null

    internal var forceRecomposition by mutableStateOf(false)
        private set

    /** Whether the overlay has been cleared. */
    var cleared by mutableStateOf(initialCleared)
        private set

    /** Clear the overlay */
    fun clear() {
        cleared = true
        cancelCalculate()
        cancelForceRecomposition()
    }

    private fun cancelCalculate() {
        _calculateJob?.cancel()
        _calculateJob = null
    }

    //
    fun onDragStart(value: Offset) {
        cancelForceRecomposition()
        path.moveTo(value.x, value.y)
    }

    private fun cancelForceRecomposition() {
        _forceRecompositionJob?.cancel()
        _forceRecompositionJob = null
        forceRecomposition = false
    }

    fun onDragAmount(value: Offset) {
        startForceRecomposition()
        path.relativeLineTo(value.x, value.y)
        redraw++
    }

    internal fun reportDraw() {
        _forceRecompositionJob?.cancel()
    }

    private fun startForceRecomposition() {
        if (_forceRecompositionJob == null) {
            _forceRecompositionJob = coroutineScope.launch {
                delay(48)
                forceRecomposition = true
            }
        }
    }


    //рассчет движения пальца с интервалом
    // вычисления во время перетаскивания, и частые вычисления запускаться не будут.
    private fun startCalculate() {
        if (_calculateJob == null) {
            _calculateJob = coroutineScope.launch {
                snapshotFlow { redraw }
                    //интервал вычисления
                    .sample(500)
                    .collect {
                        val progress = calculateProgress()
                        if (progress >= getClearThreshold()) {
                            clear()
                        }
                    }
            }
        }
    }

    private suspend fun calculateProgress(): Float = withContext(Dispatchers.Default) {
        val bitmap = graphicsLayer.toImageBitmap()
        val totalCount = bitmap.width * bitmap.height
        if (totalCount > 0) {
            // Cache buffer?
            IntArray(totalCount)
                .also { bitmap.readPixels(it) }
                .fold(0) { acc, pixel -> if (pixel == 0) acc + 1 else acc }
                .let { it.toFloat() / totalCount }
        } else {
            0f
        }
    }


    companion object {
        internal val Saver = listSaver(
            save = { listOf(it.cleared) },
            restore = { ScratchcardState(initialCleared = it[0]) },
        )
    }

}
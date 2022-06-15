package extensions

import com.soywiz.klock.Frequency
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.addUpdater
import com.soywiz.korio.lang.Cancellable
import config.GameStatus

/**
 * Wrapper over [addUpdater] function to add pausing mechanism
 */
fun Container.addUpdaterWithPause(function: () -> Unit) = addUpdater { if (GameStatus.pause.not()) function.invoke() }

/**
 * Wrapper over [addFixedUpdater] function to add pausing mechanism
 */
fun Container.addFixedUpdaterWithPause(timesPerSecond: Frequency,
                                       initial: Boolean = true,
                                       limitCallsPerFrame: Int = 16,
                                       function: () -> Unit): Cancellable {
    return addFixedUpdater(timesPerSecond, initial, limitCallsPerFrame) { if (GameStatus.pause.not()) function.invoke() }
}
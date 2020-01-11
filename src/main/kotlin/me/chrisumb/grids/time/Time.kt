package me.chrisumb.grids.time

import java.util.concurrent.TimeUnit

object Time {

    private val secondTimer = FrequencyTimer.timesPer(1, TimeUnit.SECONDS)

    private val renderTimer = FrequencyTimer.timesPer(244, TimeUnit.SECONDS)
    private val renderDeltaTimer = FrequencyTimer.timesPer(1, TimeUnit.SECONDS)

    private val tickTimer = FrequencyTimer.timesPer(64, TimeUnit.SECONDS)
    private val tickDeltaTimer = FrequencyTimer.timesPer(1, TimeUnit.SECONDS)

    val time: Float
        get() = secondTimer.count().toFloat()

    var tickDeltaTime: Float = 0f
        private set

    var renderDeltaTime: Float = 0f
        private set

    val shouldRender: Boolean
        get() = renderTimer.isReady

    val shouldTick: Boolean
        get() = tickTimer.isReady

    fun handlePreTick() {
        tickTimer.decrement()
        tickDeltaTime = tickDeltaTimer.countAndReset().toFloat()
    }

    fun handlePreRender() {
        renderDeltaTime = renderDeltaTimer.countAndReset().toFloat()
    }

}
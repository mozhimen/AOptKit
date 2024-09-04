package com.mozhimen.optk.fps.impls

import android.view.Choreographer
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.optk.fps.commons.IOptKFpsListener
import java.util.concurrent.TimeUnit

/**
 * @ClassName FrameMonitor
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 20:01
 * @Version 1.0
 */
internal class ChoreographerFrameCallback : Choreographer.FrameCallback, IUtilK {
    private val _choreographer by lazy { Choreographer.getInstance() }
    private var _lastFrameTimeNanos: Long = 0//这个是记录上一针到达的时间戳
    private var _frameCount = 0//1s内确切绘制了多少帧
    private var _iOptKFpsListeners = mutableListOf<IOptKFpsListener>()

    ////////////////////////////////////////////////////////////////////////////////

    fun addListener(vararg listener: IOptKFpsListener) {
        _iOptKFpsListeners.addAll(listener)
    }

    fun removeListeners() {
        _iOptKFpsListeners.clear()
    }

    fun start() {
        _choreographer.postFrameCallback(this)
    }

    fun stop() {
        _lastFrameTimeNanos = 0
        _choreographer.removeFrameCallback(this)
    }

    ////////////////////////////////////////////////////////////////////////////////

    override fun doFrame(frameTimeNanos: Long) {
        //帧数和耗时统计
        val currentTimeMills: Long = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)
        if (_lastFrameTimeNanos > 0) {
            //计算两针之间的时间差
            // 500ms 100ms
            val timeSpan: Long = currentTimeMills - _lastFrameTimeNanos
            //fps每秒多少帧frame per second
            _frameCount++
            if (timeSpan > 1000) {
                val fps: Double = _frameCount * 1000 / timeSpan.toDouble()
                for (listener in _iOptKFpsListeners)
                    listener.onFrame(fps)
                _frameCount = 0
                _lastFrameTimeNanos = currentTimeMills
            }
        } else {
            _lastFrameTimeNanos = currentTimeMills
        }
        start()
    }
}

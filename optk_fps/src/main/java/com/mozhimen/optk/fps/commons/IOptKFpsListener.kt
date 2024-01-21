package com.mozhimen.optk.fps.commons

/**
 * @ClassName IOptKFpsListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 16:03
 * @Version 1.0
 */
interface IOptKFpsListener {
    fun onFrame(fps: Double)
}
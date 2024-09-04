package com.mozhimen.optk.fps

import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.optk.fps.commons.IOptKFps
import com.mozhimen.optk.fps.helpers.OptKFpsDelegate

/**
 * @ClassName OptKFps
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
@OApiInit_InApplication
@OPermission_SYSTEM_ALERT_WINDOW
class OptKFps : IOptKFps by OptKFpsDelegate() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = OptKFps()
    }
}
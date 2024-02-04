package com.mozhimen.optk.fps

import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
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
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
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
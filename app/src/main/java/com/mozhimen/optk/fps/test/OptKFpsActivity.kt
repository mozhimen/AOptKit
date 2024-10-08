package com.mozhimen.optk.fps.test

import android.os.Bundle
import com.mozhimen.bindk.bases.viewdatabinding.activity.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.kotlin.utilk.android.widget.showToast
import com.mozhimen.optk.fps.OptKFps
import com.mozhimen.optk.fps.test.databinding.ActivityOptkFpsBinding


/**
 * @ClassName OptKFpsActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/15 17:16
 * @Version 1.0
 */
class OptKFpsActivity : BaseActivityVDB<ActivityOptkFpsBinding>() {
    @OptIn(OPermission_SYSTEM_ALERT_WINDOW::class, OApiInit_InApplication::class)
    override fun initView(savedInstanceState: Bundle?) {
        vdb.optkFpsBtn.setOnClickListener {
            OptKFps.instance.toggle()
        }
        vdb.optkFpsBtnTip.setOnClickListener {
            OptKFps.instance.isOpen().toString().showToast()
        }
    }
}
package com.mozhimen.optk.fps.test

import android.os.Bundle
import com.mozhimen.basick.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optin.OptInApiInit_InApplication
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
@OptIn(OptInApiInit_InApplication::class)
class OptKFpsActivity : BaseActivityVDB<ActivityOptkFpsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.optkFpsBtn.setOnClickListener {
            OptKFps.instance.toggle()
        }
        vdb.optkFpsBtnTip.setOnClickListener {
            OptKFps.instance.isOpen().toString().showToast()
        }
    }
}
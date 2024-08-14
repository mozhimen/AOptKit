package com.mozhimen.optk.fps.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.PixelFormat
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.mozhimen.basick.elemk.android.view.cons.CWinMgr
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_GetNonnull
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.wrapper.UtilKPermission
import com.mozhimen.basick.utilk.android.app.UtilKActivityStart
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.view.UtilKWindowManager
import com.mozhimen.basick.utilk.java.text.getStrDecimal_of1
import com.mozhimen.optk.fps.R
import com.mozhimen.optk.fps.commons.IOptKFps
import com.mozhimen.optk.fps.commons.IOptKFpsListener
import com.mozhimen.optk.fps.impls.ChoreographerFrameCallback

/**
 * @ClassName FpsViewer
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 16:04
 * @Version 1.0
 */
@OApiInit_InApplication
@OPermission_SYSTEM_ALERT_WINDOW
class OptKFpsDelegate : IOptKFps, BaseUtilK() {
    private val _params by lazy {
        WindowManager.LayoutParams().apply {
            width = CWinMgr.Lp.WRAP_CONTENT
            height = CWinMgr.Lp.WRAP_CONTENT
            flags = CWinMgr.Lpf.NOT_FOCUSABLE or CWinMgr.Lpf.NOT_TOUCHABLE or CWinMgr.Lpf.NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            gravity = Gravity.END or Gravity.BOTTOM
            type = if (UtilKBuildVersion.isAfterV_26_8_O())
                CWinMgr.Lpt.APPLICATION_OVERLAY else CWinMgr.Lpt.TOAST
        }
    }
    private var _isOpen = false
    private val _internalListener by lazy {
        object : IOptKFpsListener {
            @SuppressLint("SetTextI18n")
            override fun onFrame(fps: Double) {
                _optKFpsView?.text = "${fps.getStrDecimal_of1()} fps"
            }
        }
    }

    //    private var _fpsView: TextView = LayoutInflater.from(_context).inflate(R.layout.optk_fps_view, null, false) as TextView
    private var _optKFpsView: TextView? by VarProperty_GetNonnull { LayoutInflater.from(_context).inflate(R.layout.optk_fps_view, null, false) as TextView }
    private val _choreographerFrameCallback by lazy { ChoreographerFrameCallback() }
    private val _windowManager: WindowManager by lazy { UtilKWindowManager.get(_context) }

    ////////////////////////////////////////////////////////////////////////////////

    init {
        StackKCb.instance.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean, activity: Activity) {
//                if (isFront) {
//                    LogK.d(TAG, "OptKFpsView onChanged fps start")
//                    start()
//                } else {
//                    LogK.w(TAG, "OptKFpsView onChanged fps stop")
//                    stop()
//                }
                if (!isFront && isOpen()) {
                    UtilKLogWrapper.w(TAG, "OptKFpsView onChanged fps stop")
                    stop()
                }
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////

    override fun isOpen(): Boolean =
        _isOpen

    override fun toggle() {
        if (_isOpen)
            stop()
        else
            start()
    }

    override fun addListener(listener: IOptKFpsListener) {
        _choreographerFrameCallback.addListener(listener)
    }

    override fun removeListeners() {
        _choreographerFrameCallback.removeListeners()
    }

    override fun stop() {
        if (!_isOpen) return
        _isOpen = false
        _choreographerFrameCallback.stop()
        _choreographerFrameCallback.removeListeners()
        _windowManager.removeView(_optKFpsView)
        _optKFpsView = null
    }

    override fun start() {
        UtilKLogWrapper.d(TAG,"start")
        if (_isOpen) return
        if (!UtilKPermission.hasSystemAlertWindow()) {
            UtilKActivityStart.startSettingManageOverlayPermission(_context)
            UtilKLogWrapper.e(TAG, "OptKFpsView play app has no overlay permission")
            return
        }

        _isOpen = true
        _windowManager.addView(_optKFpsView, _params)
        _choreographerFrameCallback.addListener(_internalListener)
        _choreographerFrameCallback.start()
    }
}
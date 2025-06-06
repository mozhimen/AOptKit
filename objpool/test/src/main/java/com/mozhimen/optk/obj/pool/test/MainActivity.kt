package com.mozhimen.optk.obj.pool.test

import android.os.Bundle
import com.mozhimen.uik.databinding.bases.viewbinding.activity.BaseActivityVB
import com.mozhimen.kotlin.elemk.java.util.cons.CDateFormat
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.java.text.longDate2strDate
import com.mozhimen.optk.objpool.pool.OptKObjPool
import com.mozhimen.optk.obj.pool.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVB<ActivityMainBinding>() {
    private var _obj: TestClass? = null

    override fun initView(savedInstanceState: Bundle?) {
        vb.mainApplyFor.setOnClickListener {
            if (_obj == null) {
                _obj = OptKObjPool.instance.applyFor(TestClass::class.java)
            }
        }

        vb.mainGiveBack.setOnClickListener {
            if (_obj != null) {
                OptKObjPool.instance.giveBack(_obj!!)
                _obj = null
            }
        }

        vb.mainInvoke.setOnClickListener {
            _obj?.printTime()
        }

        vb.mainGetAll.setOnClickListener {
            UtilKLogWrapper.d(TAG, "mainGetAll: ${OptKObjPool.instance.getLockedObjs(TestClass::class.java)}")
        }

        vb.mainRecycleAll.setOnClickListener {
            OptKObjPool.instance.recycleAll()
        }
    }

    class TestClass : IUtilK {
        private var _createTime = 0L

        init {
            _createTime = System.currentTimeMillis()
        }

        fun printTime() {
            UtilKLogWrapper.d(TAG, "printTime: ${_createTime.longDate2strDate(CDateFormat.Format.yyyy_MM_dd_HH_mm_ss)}");
        }
    }
}
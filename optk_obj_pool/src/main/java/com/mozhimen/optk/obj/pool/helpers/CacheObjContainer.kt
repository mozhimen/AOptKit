package com.mozhimen.optk.obj.pool.helpers

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.optk.obj.pool.mos.CacheObj

/**
 * @ClassName CacheObjContainer
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
class CacheObjContainer(private val _clazz: Class<*>) : IUtilK {
    private var _cacheObjs: MutableList<CacheObj> = ArrayList()

    ///////////////////////////////////////////////////////////////

    @Synchronized
    fun getCacheObjs(): MutableList<CacheObj> {
        return _cacheObjs
    }

    @Synchronized
    fun setCacheObjs(cacheObjs: MutableList<CacheObj>) {
        _cacheObjs = cacheObjs
    }

    @Synchronized
    fun giveBack(obj: Any) {
        for (cacheObj in _cacheObjs) {
            if (cacheObj.obj === obj) {
                cacheObj.isUsing = false
                break
            }
        }
    }

    @Synchronized
    fun applyFor(): Any? {
        for ((isUsing, obj) in _cacheObjs) {
            if (!isUsing) {
                return obj
            }
        }
        try {
            val obj: Any = _clazz.getDeclaredConstructor().newInstance()
            val cacheObject = CacheObj(true, obj)
            _cacheObjs.add(cacheObject)
            return obj
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, e)
        }
        return null
    }

    @Synchronized
    fun recycleAll() {
        _cacheObjs.clear()
    }
}
package com.mozhimen.optk.objpool.pool.helpers

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.optk.objpool.pool.commons.IOptKObjContainer
import com.mozhimen.optk.objpool.pool.mos.LockedObj

/**
 * @ClassName CacheObjContainer
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
class ObjContainer<T : Any>(private val _clazz: Class<T>) : IOptKObjContainer<T> {
    private var _lockedObjs: MutableList<LockedObj<T>> = ArrayList()

    ///////////////////////////////////////////////////////////////

    @Synchronized
    override fun getLockedObjs(): List<LockedObj<T>> {
        return _lockedObjs
    }

//    @Synchronized
//    override fun setObjs(objs: List<T>) {
//        _lockedObjs = objs.map { LockedObj(false, it) }.toMutableList()
//    }

    @Synchronized
    override fun recycleAll() {
        _lockedObjs.clear()
    }

    @Synchronized
    override fun giveBack(obj: T?) {
        if (obj == null) return
        for (cacheObj in _lockedObjs) {
            if (cacheObj.obj === obj) {
                cacheObj.isUsed = false
                break
            }
        }
    }

    @Synchronized
    override fun applyFor(key: Class<T>?): T? {
        for (lockedObj in _lockedObjs) {
            if (!lockedObj.isUsed) {
                return lockedObj.apply { isUsed = true }.obj
            }
        }
        try {
            val obj: T = _clazz.getDeclaredConstructor().newInstance()
            val lockedObj = LockedObj(true, obj)
            _lockedObjs.add(lockedObj)
            return obj
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, e)
        }
        return null
    }
}
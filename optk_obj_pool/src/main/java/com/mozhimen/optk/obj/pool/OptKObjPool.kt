package com.mozhimen.optk.obj.pool

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.optk.obj.pool.commons.IOptKObjPool
import com.mozhimen.optk.obj.pool.helpers.ObjContainer
import com.mozhimen.optk.obj.pool.mos.LockedObj


/**
 * @ClassName OptKObjPool
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
class OptKObjPool : IOptKObjPool {
    private val _cacheObjContainers = HashMap<String, ObjContainer<Any>>()

    //////////////////////////////////////////////////////////////////////

    @Synchronized
    override fun <T : Any> applyFor(clazz: Class<T>): T? {
        var obj: T? = null
        try {
            val className: String = clazz.getName()
            if (_cacheObjContainers.containsKey(className)) {
                return _cacheObjContainers[className]!!.applyFor(null) as? T?
            } else {
                val container = ObjContainer(clazz)
                _cacheObjContainers[className] = container as ObjContainer<Any>
                return container.applyFor(null) as? T?
            }
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, e)
            try {
                obj = clazz.getDeclaredConstructor().newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
                UtilKLogWrapper.e(TAG, e)
            }
        }
        return obj
    }

    @Synchronized
    override fun <T : Any> giveBack(obj: T) {
        val clazzName: String = obj.javaClass.getName()
        if (_cacheObjContainers.containsKey(clazzName)) {
            _cacheObjContainers[clazzName]!!.giveBack(obj)
        }
    }

    @Synchronized
    override fun <T : Any> recycle(obj: T) {
        val clazzName: String = obj.javaClass.getName()
        if (_cacheObjContainers.containsKey(clazzName)) {
            _cacheObjContainers[clazzName]!!.recycleAll()
        }
        _cacheObjContainers.remove(clazzName)
    }

    @Synchronized
    override fun <T : Any> getLockedObjs(clazz: Class<T>): List<LockedObj<T>> {
        val clazzName: String = clazz.getName()
        return if (_cacheObjContainers.containsKey(clazzName)) {
            _cacheObjContainers[clazzName]!!.getLockedObjs() as? List<LockedObj<T>>? ?: emptyList()
        } else
            emptyList()
    }

    @Synchronized
    override fun recycleAll() {
        _cacheObjContainers.keys.forEach {
            _cacheObjContainers[it]!!.recycleAll()
        }
        _cacheObjContainers.clear()
    }

    //////////////////////////////////////////////////////////////////////

    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    //////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = OptKObjPool()
    }
}


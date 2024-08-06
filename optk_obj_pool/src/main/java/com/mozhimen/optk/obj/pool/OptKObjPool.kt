package com.mozhimen.optk.obj.pool

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.optk.obj.pool.commons.IOptKObjPool
import com.mozhimen.optk.obj.pool.helpers.CacheObjContainer


/**
 * @ClassName OptKObjPool
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
class OptKObjPool : IOptKObjPool {
    private val _cacheObjContainers = HashMap<String, CacheObjContainer>()

    //////////////////////////////////////////////////////////////////////

    @Synchronized
    override fun <T : Any> applyFor(clazz: Class<T>): T? {
        var obj: T? = null
        try {
            val className: String = clazz.getName()
            if (_cacheObjContainers.containsKey(className)) {
                return _cacheObjContainers[className]!!.applyFor() as? T?
            } else {
                val container = CacheObjContainer(clazz)
                _cacheObjContainers[className] = container
                return container.applyFor() as? T?
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
    override fun recycleAll() {
        val iterator: Iterator<*> = _cacheObjContainers.keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next() as String
            _cacheObjContainers[key]!!.recycleAll()
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


package com.mozhimen.optk.objpool.pool.commons

import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.optk.objpool.pool.mos.LockedObj

/**
 * @ClassName IOptKObjPool
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
interface IOptKObjPool : IUtilK {
    fun <T : Any> applyFor(clazz: Class<T>): T?
    fun <T : Any> giveBack(obj: T)
    fun <T : Any> recycle(obj: T)
    fun <T : Any> getLockedObjs(clazz: Class<T>): List<LockedObj<T>>
    fun recycleAll()
}
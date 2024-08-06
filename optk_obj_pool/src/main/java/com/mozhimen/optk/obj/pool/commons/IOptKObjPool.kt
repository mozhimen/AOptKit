package com.mozhimen.optk.obj.pool.commons

import com.mozhimen.basick.utilk.commons.IUtilK

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
    fun recycleAll()
}
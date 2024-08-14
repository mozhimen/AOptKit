package com.mozhimen.optk.obj.pool.commons

import com.mozhimen.basick.elemk.commons.IApplicable
import com.mozhimen.basick.elemk.commons.IReturnable
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.optk.obj.pool.mos.LockedObj

/**
 * @ClassName IOptKObjContainer
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
interface IOptKObjContainer<T : Any> : IReturnable<T>, IApplicable<Class<T>, T>, IUtilK {
    fun getLockedObjs(): List<LockedObj<T>>

    //    fun setObjs(objs: List<T>)
    fun recycleAll()
}
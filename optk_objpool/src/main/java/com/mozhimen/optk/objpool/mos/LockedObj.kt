package com.mozhimen.optk.objpool.pool.mos

/**
 * @ClassName CacheObj
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/6
 * @Version 1.0
 */
data class LockedObj<T : Any>(
    var isUsed: Boolean,
    var obj: T
)

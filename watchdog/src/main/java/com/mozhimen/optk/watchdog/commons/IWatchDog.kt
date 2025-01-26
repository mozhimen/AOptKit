package com.mozhimen.optk.watchdog.commons

import com.mozhimen.kotlin.utilk.commons.IUtilK

/**
 * @ClassName IWatchDog
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/10/28
 * @Version 1.0
 */
interface IWatchDog<T> : IUtilK {
    fun feed()
    fun bark(): T?
}
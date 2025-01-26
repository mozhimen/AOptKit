package com.mozhimen.optk.watchdog

import com.mozhimen.optk.watchdog.commons.IWatchDog
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName OptKWatchDog
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/10/28
 * @Version 1.0
 */
class OptKWatchDogs {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ////////////////////////////////////////////////////////////////////

    private var _watchDogs: MutableMap<String, IWatchDog<*>> = ConcurrentHashMap<String, IWatchDog<*>>()

    ////////////////////////////////////////////////////////////////////

    @Synchronized
    fun add(key: String, watchDog: IWatchDog<*>) {
        if (!_watchDogs.containsKey(key)) {
            _watchDogs[key] = watchDog
        }
    }

    @Synchronized
    fun feed() {
        _watchDogs.values.forEach {
            it.feed()
        }
    }

    @Synchronized
    fun <T> bark(key: String): T? {
        return if (_watchDogs.containsKey(key)) {
            _watchDogs[key] as? T?
        } else
            null
    }

    ////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = OptKWatchDogs()
    }
}
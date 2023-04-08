package me.wcy.common.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.wcy.common.CommonApp

/**
 * @author wcy
 * @date 2018/7/20
 */
object ActivityRecorder {
    private val activityList = mutableListOf<Activity>()
    private var foregroundActivityCount = 0
    private val appForegroundInternal = MutableLiveData(false)

    val appForeground: LiveData<Boolean> = appForegroundInternal

    private val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            synchronized(activityList) {
                activityList.add(activity)
            }
        }

        override fun onActivityStarted(activity: Activity) {
            foregroundActivityCount++
            if (appForegroundInternal.value == false) {
                appForegroundInternal.value = true
            }
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            foregroundActivityCount--
            if (foregroundActivityCount == 0 && appForegroundInternal.value == true) {
                appForegroundInternal.value = false
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            synchronized(activityList) {
                activityList.remove(activity)
            }
        }
    }

    init {
        CommonApp.app.registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    fun getStackSize(): Int {
        synchronized(activityList) {
            return activityList.size
        }
    }

    fun getTopActivity(): Activity? {
        synchronized(activityList) {
            return activityList.lastOrNull()
        }
    }

    /**
     * 结束指定 activity
     */
    fun finishActivity(clazz: Class<out Activity>) {
        synchronized(activityList) {
            val iterator = activityList.asReversed().iterator()
            while (iterator.hasNext()) {
                val activity = iterator.next()
                if (activity.javaClass.canonicalName == clazz.canonicalName) {
                    activity.finish()
                }
            }
        }
    }

    /**
     * 清除 except 外其他所有 activity
     */
    fun finishActivityExcept(except: Class<out Activity>) {
        synchronized(activityList) {
            val iterator = activityList.asReversed().iterator()
            while (iterator.hasNext()) {
                val activity = iterator.next()
                if (activity.javaClass.canonicalName != except.canonicalName) {
                    activity.finish()
                }
            }
        }
    }

    @JvmStatic
    fun clearStack() {
        synchronized(activityList) {
            val iterator = activityList.asReversed().iterator()
            while (iterator.hasNext()) {
                val activity = iterator.next()
                activity.finish()
            }
        }
    }
}
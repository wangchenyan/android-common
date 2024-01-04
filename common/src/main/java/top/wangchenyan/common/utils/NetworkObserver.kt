package top.wangchenyan.common.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.coroutines.launch
import top.wangchenyan.common.CommonApp

/**
 * Created by wangchenyan.top on 2022/9/29.
 */
class NetworkObserver(lifecycle: Lifecycle?) {
    private var networkListener: NetworkUtils.OnNetworkStatusChangedListener? = null

    init {
        lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                networkListener?.let {
                    NetworkUtils.unregisterNetworkStatusChangedListener(it)
                }
            }
        })
    }

    /**
     * 一个对象仅支持调用一次，多次调用会导致之前的监听失效
     */
    fun onNetworkConnectOnce(callback: () -> Unit) {
        if (networkListener != null) {
            NetworkUtils.unregisterNetworkStatusChangedListener(networkListener)
            networkListener = null
        }
        if (NetworkUtils.isConnected()) {
            callback()
            return
        }
        networkListener = object : NetworkUtils.OnNetworkStatusChangedListener {
            override fun onConnected(networkType: NetworkUtils.NetworkType?) {
                CommonApp.appScope.launch {
                    callback()
                    NetworkUtils.unregisterNetworkStatusChangedListener(networkListener)
                    networkListener = null
                }
            }

            override fun onDisconnected() {
            }
        }
        NetworkUtils.registerNetworkStatusChangedListener(networkListener)
    }
}
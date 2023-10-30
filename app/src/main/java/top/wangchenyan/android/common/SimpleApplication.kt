package top.wangchenyan.android.common

import android.app.Application
import android.content.Intent
import me.wcy.common.CommonApp
import me.wcy.common.ui.activity.FragmentContainerActivity
import me.wcy.router.CRouter
import me.wcy.router.RouterClient

/**
 * Created by wangchenyan.top on 2023/4/16.
 */
class SimpleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CommonApp.init {
            test = true
            imageLoaderConfig {
                placeholder = R.mipmap.ic_launcher
            }
        }
        initCRouter()
    }

    private fun initCRouter() {
        CRouter.setRouterClient(
            RouterClient.Builder()
                .baseUrl("app://common.android")
                .loginProvider { context, callback ->
                    callback()
                }
                .fragmentContainerIntentProvider {
                    Intent(it, FragmentContainerActivity::class.java)
                }
                .build()
        )
    }
}
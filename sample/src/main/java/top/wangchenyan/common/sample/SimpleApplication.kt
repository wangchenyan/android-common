package top.wangchenyan.common.sample

import android.app.Application
import android.content.Intent
import me.wcy.router.CRouter
import me.wcy.router.RouterClient
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ui.activity.FragmentContainerActivity

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
            apiConfig({}) {
                codeJsonNames = listOf("errorCode")
                msgJsonNames = listOf("errorMsg")
                dataJsonNames = listOf("data")
                successCode = 0
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
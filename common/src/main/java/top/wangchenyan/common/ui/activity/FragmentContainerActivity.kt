package top.wangchenyan.common.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import me.wcy.router.CRouter
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.ui.fragment.BackEventInterceptor

/**
 * Created by wangchenyan.top on 2022/6/8.
 */
open class FragmentContainerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT) != null) {
            return
        }

        val uri: Uri =
            intent.getParcelableExtra(CRouter.CROUTER_KEY_FRAGMENT_URI) ?: kotlin.run {
                if (CommonApp.test) {
                    throw IllegalStateException("FragmentContainerActivity only can be started by CRouter!")
                } else {
                    Log.e(TAG, "FragmentContainerActivity only can be started by CRouter!")
                    finish()
                    return
                }
            }
        val clazz = CRouter.with(this).uri(uri).getFragmentX() ?: kotlin.run {
            if (CommonApp.test) {
                throw IllegalStateException("Can not find fragment by uri: $uri")
            } else {
                Log.e(TAG, "Can not find fragment by uri: $uri")
                finish()
                return
            }
        }
        val fragment = clazz.newInstance()
        fragment.arguments = intent.extras
        fragment.arguments?.remove(CRouter.CROUTER_KEY_FRAGMENT_URI)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(android.R.id.content, fragment, TAG_FRAGMENT)
        transaction.commitNowAllowingStateLoss()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT)
        if (fragment !is BackEventInterceptor || fragment.onInterceptBackEvent().not()) {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "FragmentContainerAct"
        private const val TAG_FRAGMENT = "fragment_in_container"
    }
}
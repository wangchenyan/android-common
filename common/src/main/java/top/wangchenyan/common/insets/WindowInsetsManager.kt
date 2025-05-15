package top.wangchenyan.common.insets

import android.app.Activity
import androidx.annotation.ColorInt

/**
 * Created by wangchenyan.top on 2025/5/13.
 */
interface WindowInsetsManager {
    var fillStatusBar: Boolean

    var fillNavBar: Boolean

    var fillIme: Boolean

    var fillDisplayCutout: Boolean

    @get:ColorInt
    var statusBarColor: Int

    @get:ColorInt
    var navBarColor: Int

    @get:ColorInt
    var displayCutoutColor: Int

    var statusBarTextDarkStyle: Boolean

    var navBarButtonDarkStyle: Boolean

    companion object {
        fun Activity.obtainWindowInsetsManager(): WindowInsetsManager {
            return WindowInsetsManagerImpl(this)
        }
    }
}
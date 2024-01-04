package top.wangchenyan.common.utils.image

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader

/**
 * Created by wangchenyan.top on 2022/11/4.
 */
object ImageViewer {

    fun show(anchor: ImageView, url: String) {
        XPopup.Builder(anchor.context)
            .asImageViewer(anchor, url, SmartGlideImageLoader())
            .show()
    }

    fun show(anchor: ImageView, urls: List<String>, position: Int) {
        XPopup.Builder(anchor.context)
            .asImageViewer(
                anchor, position, urls, { popupView, pos ->
                    val srcView = (anchor.parent as? ViewGroup)?.getChildAt(pos) as? ImageView
                    srcView?.let {
                        popupView.updateSrcView(it)
                    }
                }, SmartGlideImageLoader()
            )
            .show()
    }

    fun show(context: Context, urls: List<String>, position: Int) {
        XPopup.Builder(context)
            .asImageViewer(null, position, urls, null, SmartGlideImageLoader())
            .show()
    }
}
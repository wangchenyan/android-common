package me.wcy.common.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import me.wcy.common.R

class BaseLoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.common_load_sir_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}
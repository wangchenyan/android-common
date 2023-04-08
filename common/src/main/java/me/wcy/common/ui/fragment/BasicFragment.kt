package me.wcy.common.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

/**
 * Created by wcy on 2019/7/27.
 */
abstract class BasicFragment : Fragment() {

    companion object {
        const val TAG = "WCY-Fragment"
    }

    override fun setArguments(args: Bundle?) {
        if (!isStateSaved) {
            super.setArguments(args)
        } else {
            Log.w(TAG, "Fragment already active and state has been saved")
        }
    }
}
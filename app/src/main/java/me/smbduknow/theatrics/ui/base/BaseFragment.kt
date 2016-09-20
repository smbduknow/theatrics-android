package me.smbduknow.theatrics.ui.base

import android.content.Intent
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val childFragments = childFragmentManager?.fragments ?: return
        for (child in childFragments) {
            if (child != null && !child.isDetached && !child.isRemoving) {
                child.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}
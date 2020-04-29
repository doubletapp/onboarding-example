package com.onthecrow.onboardingexample

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2

class OnboardingPageTransformer : ViewPager2.PageTransformer {

    /**
     * This method is the core of the whole page transformations. It receives the View object of the
     * current pager's page and its scroll position.
     *
     * @param page is the View object of the current page.
     * @param position is the scroll position at the ViewPager (from 1 to -1).
     */
    override fun transformPage(page: View, position: Float) {
        //Since here the FragmentStateAdapter is used, every fragment is wrapped in empty ViewHolder
        //with FrameLayout, so page argument is ViewHolder's FrameLayout and its child is root
        //layout of the nested fragment.
        (page as? ViewGroup)?.children?.first()?.let { children ->
            //This children can be used with FragmentManager to find enclosing fragment.
            FragmentManager.findFragment<Fragment>(children).run {
                //Check it implements the Transformable interface and pass the position to its
                //transform() method.
                when (this) {
                    is Transformable -> transform(position)
                }
            }
        }
    }
}
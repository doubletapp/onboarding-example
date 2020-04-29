package com.onthecrow.onboardingexample.base

import android.graphics.Point
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.onthecrow.onboardingexample.Transformable

abstract class BasePagerFragment(@LayoutRes resId: Int) : Fragment(resId), Transformable {

    protected val screenWidth by lazy { initScreenWidth() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /**
     * This method returns display's width.
     *
     * @return is a width of the screen in pixels.
     */
    private fun initScreenWidth() = Point().let {
        activity?.windowManager?.defaultDisplay?.getSize(it)
        return@let it.x
    }

    /**
     * This method returns a position based on the screen width. This position can be used at the
     * translationX property of a view to make it "static" on the screen when the ViewPager is
     * scrolled.
     *
     * @param position the view with the map background.
     * @return position that compensate the ViewPager scroll.
     */
    protected fun getFrozenPosition(position: Float): Float = screenWidth.inv().times(position)

    abstract override fun transform(position: Float)
}
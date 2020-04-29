package com.onthecrow.onboardingexample.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout

abstract class BaseMapFragment(@LayoutRes resId: Int) : BasePagerFragment(resId) {

    var maxScaleCoefficient: Float? = null

    /**
     * This method calculates the max scale coefficient for the map once it has been measured.
     *
     * @param backgroundView the view with the map background.
     * @param footerView the footer of the map screen.
     */
    protected fun calculateMaxScaleCoefficient(backgroundView: View, footerView: View) {
        //Do it on view layout to be sure that the view has width and height.
        (backgroundView.parent as View).doOnLayout {
            //Take screen width and width of the map (which is bigger than screen width), and
            //calculate the max scaling coefficient which can be used to scale the map without
            //empty spacings on its sides (when the map is less than screen by width or height).
            val widthMaxScaleCoefficient = screenWidth.toFloat() / backgroundView.width.toFloat()
            //Get layout footer position to calculate minimum map height.
            val footerLocationArray = IntArray(2)
            footerView.getLocationInWindow(footerLocationArray)
            //Calculate the max scale coefficient for the map by height analogically to what has
            //done for width above.
            val heightMaxScaleCoefficient =
                (footerLocationArray[1] + footerView.height / 2) / backgroundView.height.toFloat()
            //Choose the bigger one.
            maxScaleCoefficient = if (widthMaxScaleCoefficient > heightMaxScaleCoefficient) {
                widthMaxScaleCoefficient
            } else {
                heightMaxScaleCoefficient
            }
            //Finally call this method to notify the subclasses that the max scale coefficient
            //has been calculated.
            afterMaxScaleCoefficientIsCalculated()
        }
    }

    /**
     * This method is called when the maxScaleCoefficient is calculated.
     */
    abstract fun afterMaxScaleCoefficientIsCalculated()
}
package com.onthecrow.onboardingexample

/**
 * This interface is for pager fragments that may be transformed by the PageTransformer.
 */
interface Transformable {

    /**
     * This method is to be called by the PageTransformer.
     *
     * @param position is the position from the PageTransformer.transformPage() method.
     */
    fun transform(position: Float)
}
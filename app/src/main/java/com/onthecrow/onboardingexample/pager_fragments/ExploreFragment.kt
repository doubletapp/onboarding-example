package com.onthecrow.onboardingexample.pager_fragments

import android.os.Bundle
import android.view.View
import com.onthecrow.onboardingexample.R
import com.onthecrow.onboardingexample.base.BaseMapFragment
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlin.math.absoluteValue

class ExploreFragment : BaseMapFragment(R.layout.fragment_explore) {

    companion object {
        const val ARG_SCALE = "scale"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calculateMaxScaleCoefficient(exploreBackground, exploreLayoutFooter)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun transform(position: Float) {
        //Resize the background map when the fragment scrolls in or out from the screen using
        //pre-calculated maxScaleCoefficient field and current ViewPager scroll position.
        if (position >= 0) {
            maxScaleCoefficient?.let {
                val scaleCoefficient = it + ((1 - it) * position.absoluteValue)
                exploreBackground.scaleX = scaleCoefficient
                exploreBackground.scaleY = scaleCoefficient
                exploreBackgroundBlurred.scaleY = scaleCoefficient
                exploreBackgroundBlurred.scaleX = scaleCoefficient
            }
        }
        //Change the alpha field of the blurred map from 0 to 1 to simulate blur of the background
        //when this fragment is scrolled to/from the left.
        if (position <= 0) {
            exploreBackgroundBlurred.alpha = position.absoluteValue
        }
        //Make some views are static on the screen.
        exploreLayoutFooter.translationX = getFrozenPosition(position)
        exploreBackground.translationX = getFrozenPosition(position)
        exploreBackgroundBlurred.translationX = getFrozenPosition(position)
        //Simulate the parallax effect.
        exploreDistance.translationX = resources.getDimension(R.dimen.explore_distance_translation) + position * resources.getDimension(
            R.dimen.explore_distance_shifted
        )
        exploreButton.translationX = position * resources.getDimension(R.dimen.select_button_translation)
        exploreDescription.translationX = position * resources.getDimension(
            R.dimen.select_selected_translation
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(ARG_SCALE, exploreBackground.scaleX)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        exploreBackground.scaleX = savedInstanceState?.getFloat(ARG_SCALE) ?: 1F
        exploreBackground.scaleY = savedInstanceState?.getFloat(ARG_SCALE) ?: 1F
        exploreBackgroundBlurred.scaleY = savedInstanceState?.getFloat(ARG_SCALE) ?: 1F
        exploreBackgroundBlurred.scaleX = savedInstanceState?.getFloat(ARG_SCALE) ?: 1F
    }

    override fun afterMaxScaleCoefficientIsCalculated() { }
}
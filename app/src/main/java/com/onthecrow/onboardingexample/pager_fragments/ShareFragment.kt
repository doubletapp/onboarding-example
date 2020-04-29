package com.onthecrow.onboardingexample.pager_fragments

import android.os.Bundle
import android.view.View
import com.onthecrow.onboardingexample.R
import com.onthecrow.onboardingexample.base.BaseMapFragment
import kotlinx.android.synthetic.main.fragment_share.*
import kotlin.math.absoluteValue

class ShareFragment : BaseMapFragment(R.layout.fragment_share) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calculateMaxScaleCoefficient(shareBackground, shareFooterLayout)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun transform(position: Float) {
        //Change the alpha field of the blurred map from 0 to 1 to simulate blur of the background
        //when this fragment is scrolled to/from the right.
        if (position >= 0) {
            shareBackgroundBlurred.alpha = 1 - position.absoluteValue
        }
        //Make some views are static on the screen when the ViewPager is scrolled.
        shareFooterLayout.translationX = getFrozenPosition(position)
        shareBackground.translationX = getFrozenPosition(position)
        shareBackgroundBlurred.translationX = getFrozenPosition(position)
        //Simulate a parallax effect by multiply position to fixed dp value to make elements move
        //with different speeds when you scroll the ViewPager.
        shareButtonLayout.translationX = position * resources.getDimension(
            R.dimen.select_button_translation
        )
        shareDescription.translationX = position * resources.getDimension(
            R.dimen.select_selected_translation
        )
    }

    override fun afterMaxScaleCoefficientIsCalculated() {
        //Resize the background map to the same value as it's resized at the previous screen
        //to make a transition is seamless.
        shareBackground.scaleX = maxScaleCoefficient ?: 1F
        shareBackground.scaleY = maxScaleCoefficient ?: 1F
        shareBackgroundBlurred.scaleY = maxScaleCoefficient ?: 1F
        shareBackgroundBlurred.scaleX = maxScaleCoefficient ?: 1F
    }
}
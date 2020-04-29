package com.onthecrow.onboardingexample.pager_fragments

import com.onthecrow.onboardingexample.R
import com.onthecrow.onboardingexample.base.BasePagerFragment
import kotlinx.android.synthetic.main.fragment_select.*

class SelectFragment : BasePagerFragment(R.layout.fragment_select) {

    override fun transform(position: Float) {
        //Make the view static at the screen when the ViewPager scrolls.
        selectLayoutFooter.translationX = getFrozenPosition(position)
        //Simulate the parallax effect.
        selectSelectedItem.translationX = position * resources.getDimension(
            R.dimen.select_selected_translation
        )
        selectButton.translationX = position * resources.getDimension(R.dimen.select_button_translation)
        selectDescription.translationX = position * resources.getDimension(
            R.dimen.select_selected_translation
        )
    }
}

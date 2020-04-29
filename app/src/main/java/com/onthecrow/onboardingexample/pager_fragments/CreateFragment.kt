package com.onthecrow.onboardingexample.pager_fragments

import com.onthecrow.onboardingexample.R
import com.onthecrow.onboardingexample.base.BasePagerFragment
import kotlinx.android.synthetic.main.fragment_create.*
import kotlin.math.absoluteValue

class CreateFragment : BasePagerFragment(R.layout.fragment_create) {

    private var isCollapsed = true

    override fun transform(position: Float) {
        //Resize the list item view when this fragment is appearing at the screen from the right.
        if (position >= 0) {
            createSelectedItem.scaleX = 1 - position.absoluteValue
            createSelectedItem.scaleY = 1 - position.absoluteValue
        }
        //Show item indicator with animation when the fragment stops scrolling at the center of the
        //screen and hide it when the fragment is scrolling out to the right or scrolling in from
        //the right.
        if (position == 0F) {
            showItemIndicator(true)
        } else if (position > 0 && !isCollapsed) {
            showItemIndicator(false)
        }
        //Compensate scroll offset for the items via translationX to make it static-like on the
        //screen when it's scrolled in/out from/to the left-side of the screen.
        if (position < 0) {
            createSelectedItemIndicator.translationX = getFrozenPosition(position)
            createSelectedItem.translationX = getFrozenPosition(position)
            createList.translationX = getFrozenPosition(position)
        }
        createLayoutFooter.translationX = getFrozenPosition(position)
        //Simulate a parallax effect by multiply position to fixed dp value to make elements move
        //with different speeds when you scroll the ViewPager.
        createButton.translationX =
            position * resources.getDimension(R.dimen.select_button_translation)
        createDescription.translationX = position * resources.getDimension(
            R.dimen.select_selected_translation
        )
    }

    /**
     * This method shows and hides the violet item dot-indicator with animation
     *
     * @param isShow is a boolean value which defines that indicator will be shown or hidden.
     */
    private fun showItemIndicator(isShow: Boolean) {
        isCollapsed = !isShow
        createSelectedItemIndicator.animate()
            .scaleX(if (isShow) 1f else 0f)
            .scaleY(if (isShow) 1f else 0f)
            .setDuration(150)
            .start()
    }
}
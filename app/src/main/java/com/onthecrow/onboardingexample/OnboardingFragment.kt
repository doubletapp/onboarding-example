package com.onthecrow.onboardingexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val pagerAdapter by lazy { OnboardingAdapter(this) }

    private var buttonAnimator: ViewPropertyAnimatorCompat? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onboardingViewPager.adapter = pagerAdapter
        //The all magic happens here when you set the PageTransformer to the ViewPager
        onboardingViewPager.setPageTransformer(OnboardingPageTransformer())
        //Use TabLayoutMediator to connect ViewPager2 and TabLayout.
        TabLayoutMediator(onboardingTabLayout, onboardingViewPager) { _, _ -> }.attach()
        onboardingViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    invalidateButtons(position)
                }
            }
        )
        onboardingSkipButton.setOnClickListener { skip() }
        onboardingStartButton.setOnClickListener { skip() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun skip() {
        activity?.finish()
    }

    /**
     * This method changes bottom buttons visibility depending on the current ViewPager position.
     *
     * @param position is the current ViewPager position.
     */
    private fun invalidateButtons(position: Int) {
        val isLastItem = position == OnboardingAdapter.ONBOARDING_ITEMS_COUNT.dec()
        val isStartButtonVisible = onboardingStartButton.visibility == View.VISIBLE
        if (isLastItem && !isStartButtonVisible) {
            showButton(onboardingStartButton, onboardingSkipButton)
        } else if (!isLastItem && isStartButtonVisible) {
            showButton(onboardingSkipButton, onboardingStartButton)
        }
    }

    /**
     * This method hides one button and shows another using the ViewPropertyAnimatorCompat.
     *
     * @param buttonToShow is the button that will be shown.
     * @param buttonToHide is the button that will be hidden.
     */
    private fun showButton(buttonToShow: Button, buttonToHide: Button) {
        buttonToShow.alpha = 0f
        buttonToShow.visibility = View.VISIBLE
        buttonAnimator?.cancel()
        buttonAnimator = ViewCompat.animate(buttonToHide)
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                buttonToHide.visibility = View.GONE
                ViewCompat.animate(buttonToShow)
                    .alpha(1f)
                    .setDuration(300)
                    .start()
            }
        buttonAnimator?.start()
    }
}
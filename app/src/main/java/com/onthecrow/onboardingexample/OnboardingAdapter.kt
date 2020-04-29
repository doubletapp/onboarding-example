package com.onthecrow.onboardingexample

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onthecrow.onboardingexample.pager_fragments.CreateFragment
import com.onthecrow.onboardingexample.pager_fragments.ExploreFragment
import com.onthecrow.onboardingexample.pager_fragments.SelectFragment
import com.onthecrow.onboardingexample.pager_fragments.ShareFragment

class OnboardingAdapter(fragment: OnboardingFragment) : FragmentStateAdapter(fragment) {

    companion object {
        const val ONBOARDING_ITEMS_COUNT = 4
        const val POSITION_SELECT = 0
        const val POSITION_CREATE = 1
        const val POSITION_EXPLORE = 2
        const val POSITION_SHARE = 3
    }

    override fun createFragment(position: Int) = when (position) {
        POSITION_SELECT -> SelectFragment()
        POSITION_CREATE -> CreateFragment()
        POSITION_EXPLORE -> ExploreFragment()
        POSITION_SHARE -> ShareFragment()
        else -> Fragment()
    }

    override fun getItemCount() = ONBOARDING_ITEMS_COUNT
}
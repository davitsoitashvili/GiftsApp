package com.example.giftsapp.ui.fragments.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentDashboardBinding
import com.example.giftsapp.tools.extensions.navigate
import com.example.giftsapp.ui.adapters.DashboardPagerAdapter

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private lateinit var binding: FragmentDashboardBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        initDashboardPagerAdapter()
    }

    private fun initDashboardPagerAdapter() {
        val dashboardViewPager = binding.dashboardPagerAdapter
        dashboardViewPager.adapter =
            DashboardPagerAdapter(requireActivity()) {
                if (it) {
                    navigate(R.id.signInFragment)
                }
            }

        binding.dashboardNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.postsFragment -> dashboardViewPager.currentItem = 0
                R.id.myPostsFragment -> dashboardViewPager.currentItem = 1
                R.id.createPostFragment -> dashboardViewPager.currentItem = 2
                R.id.profileFragment -> dashboardViewPager.currentItem = 3
            }
            true
        }

        dashboardViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }
            override fun onPageSelected(position: Int) {
                binding.dashboardNavView.menu.getItem(position).isChecked = true
            }
        })
    }
}

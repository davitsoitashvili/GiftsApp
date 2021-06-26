package com.example.giftsapp.ui.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.giftsapp.ui.fragments.dashboard.create_post.CreatePostFragment
import com.example.giftsapp.ui.fragments.dashboard.my_posts.MyPostsFragment
import com.example.giftsapp.ui.fragments.dashboard.posts.PostsFragment
import com.example.giftsapp.ui.fragments.dashboard.profile.ProfileFragment

class DashboardPagerAdapter(fragmentActivity: FragmentActivity, isSignedOut : (Boolean) -> Unit) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = arrayOf(
        PostsFragment(),
        MyPostsFragment(),
        CreatePostFragment(),
        ProfileFragment(isSignedOut)
    )
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}
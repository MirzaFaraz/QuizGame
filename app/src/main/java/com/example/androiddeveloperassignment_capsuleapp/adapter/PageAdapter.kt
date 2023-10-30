package com.example.androiddeveloperassignment_capsuleapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androiddeveloperassignment_capsuleapp.MainActivity

class PageAdapter(
    activity: MainActivity, private val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}

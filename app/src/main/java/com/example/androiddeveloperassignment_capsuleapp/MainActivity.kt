package com.example.androiddeveloperassignment_capsuleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.androiddeveloperassignment_capsuleapp.adapter.PageAdapter
import com.example.androiddeveloperassignment_capsuleapp.capsule.NotesFragment
import com.example.androiddeveloperassignment_capsuleapp.capsule.QuizFragment
import com.example.androiddeveloperassignment_capsuleapp.capsule.VideoFragment
import com.example.androiddeveloperassignment_capsuleapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        initViews()
        initListeners()


    }

    private fun initListeners() {
        binding!!.proceedBtn.setOnClickListener(this)
    }

    private fun initViews() {
        supportActionBar?.elevation = 0F
        //Initializing viewPager


        val list: List<Fragment> = listOf(VideoFragment(), NotesFragment(), QuizFragment())
        val adapter = PageAdapter(this, list)
        binding!!.viewPager.adapter = adapter


         TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            tab.text = getTitle(position)
        }.attach()









    }

    private fun getTitle(position: Int): String? {
        return when (position) {
            0 -> "Video"
            1 -> "Notes"
            2 -> "Quiz"
            else -> null
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onClick(view: View?) {

        if (view == binding!!.proceedBtn) {
            var index = binding!!.viewPager.currentItem
            if (index <= 1) {
                index++
                binding!!.viewPager.currentItem = index
            }

        }
    }
}
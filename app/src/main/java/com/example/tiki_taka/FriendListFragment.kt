package com.example.tiki_taka

import FriendListFragmentAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.tiki_taka.databinding.FragmentFriendLevel1Binding
import com.example.tiki_taka.databinding.FragmentFriendLevel2Binding
import com.example.tiki_taka.databinding.FragmentFriendLevel3Binding
import com.example.tiki_taka.databinding.FragmentFriendListBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FriendListFragment : Fragment() {
    private lateinit var binding: FragmentFriendListBinding

    //TabLayout 구현에 필요한 부분
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendListBinding.inflate(layoutInflater)

        viewPager = binding.viewFriendList
        tabLayout = binding.tabFriendList

        // Create a list of fragment titles
        val tabTitles = listOf("Level 1", "Level 2", "Level 3")

        // Create a list of fragments for the ViewPager2
        val fragments = listOf(
            FriendListTab1Fragment(),
            FriendListTab2Fragment(),
            FriendListTab3Fragment()
        )

        // Set up the ViewPager2 with the fragments
        viewPager.adapter = FriendListFragmentAdapter(this, fragments)

        // Connect the TabLayout with the ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val tabLayout = binding.tabFriendList
//        val viewPager: ViewPager2 = binding.viewFriendList
//        val adapter = FriendListPagerAdapter(requireActivity())
//
//        viewPager.adapter = adapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when (position) {
//                0 -> tab.text = "Level 1"
//                1 -> tab.text = "Level 2"
//                2 -> tab.text = "Level 3"
//            }
//        }.attach()
    }
}

/*class FriendListPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            2 -> Tab3Fragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}

class Tab1Fragment : Fragment() {
    private lateinit var binding: FragmentFriendLevel1Binding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendLevel1Binding.inflate(inflater, container, false)
        recyclerView = binding.recyclerLevel1
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = YourTab1RecyclerViewAdapter()

        return binding.root
    }
}

class Tab2Fragment : Fragment() {
    private lateinit var binding: FragmentFriendLevel2Binding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendLevel2Binding.inflate(inflater, container, false)
        recyclerView = binding.recyclerLevel2
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = YourTab2RecyclerViewAdapter()

        return binding.root
    }
}

class Tab3Fragment : Fragment() {
    private lateinit var binding: FragmentFriendLevel3Binding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendLevel3Binding.inflate(inflater, container, false)
        recyclerView = binding.recyclerLevel3
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = YourTab3RecyclerViewAdapter()

        return binding.root
    }
}*/
package com.androidkamallib.libapplication.view.dashboard.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.androidkamallib.libapplication.databinding.FragmentHomeBinding
import com.androidkamallib.libapplication.util.factory.viewModelFactory.HomeViewModelFactory
import com.androidkamallib.libapplication.view.dashboard.ui.home.viewModel.HomeViewModel
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var fragmentSelectCityBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentSelectCityBinding =
            FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel =
            ViewModelProviders.of(this, HomeViewModelFactory(activity as BaseActivity, this))
                .get(
                    HomeViewModel::class.java
                )
        fragmentSelectCityBinding.viewModel = homeViewModel
        fragmentSelectCityBinding.lifecycleOwner = this
        return fragmentSelectCityBinding.root
    }
}
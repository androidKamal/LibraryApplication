package com.androidkamallib.libapplication.view.dashboard.ui.city.fragemnt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.androidkamallib.libapplication.databinding.FragmentSelectCityBinding
import com.androidkamallib.libapplication.util.factory.viewModelFactory.SelectCityViewModelFactory
import com.androidkamallib.libapplication.view.dashboard.ui.city.viewModel.SelectCityViewModel
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseFragment

class SelectCityFragment : BaseFragment() {

    private lateinit var toolsViewModel: SelectCityViewModel

    lateinit var fragmentSelectCityBinding: FragmentSelectCityBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this, SelectCityViewModelFactory(activity as BaseActivity, this))
                .get(
                    SelectCityViewModel::class.java
                )
        fragmentSelectCityBinding =
            FragmentSelectCityBinding.inflate(inflater, container, false)
        fragmentSelectCityBinding.viewModel = toolsViewModel
        fragmentSelectCityBinding.lifecycleOwner = this
        return fragmentSelectCityBinding.root
    }
}
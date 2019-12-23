package com.androidkamallib.libapplication.view.splash_activity.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.androidkamallib.libapplication.R
import com.androidkamallib.libapplication.app.MyApplication
import com.androidkamallib.libapplication.databinding.ActivityMainBinding
import com.androidkamallib.libapplication.di.WeatherApplicationComponent
import com.androidkamallib.libapplication.util.factory.viewModelFactory.SplashViewModelFactory
import com.androidkamallib.libapplication.view.splash_activity.viewModel.SplashViewModel
import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.dagger.module.data.preference.SharedPrefsHelper

class SplashActivity : BaseActivity() {

    @javax.inject.Inject
    lateinit var preferenceHelper: SharedPrefsHelper


    private var binding: ActivityMainBinding? = null
    var viewmodel: SplashViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (((applicationContext as MyApplication).applicationComponent) as WeatherApplicationComponent).inject(
            this
        )
        viewmodel = ViewModelProviders.of(this, SplashViewModelFactory(this))
            .get(SplashViewModel::class.java)
        binding!!.viewModel = viewmodel
        binding!!.lifecycleOwner = this
    }


}

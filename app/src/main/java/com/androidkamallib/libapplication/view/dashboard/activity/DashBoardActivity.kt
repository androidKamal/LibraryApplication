package com.androidkamallib.libapplication.view.dashboard.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.androidkamallib.libapplication.R
import com.androidkamallib.libapplication.databinding.ActivityDashBoardBinding
import com.androidkamallib.libapplication.util.factory.viewModelFactory.DashboardViewModelFactory
import com.androidkamallib.libapplication.view.dashboard.ui.city.fragemnt.SelectCityFragment
import com.androidkamallib.libapplication.view.dashboard.viewModel.DashboardViewModel
import com.androidkamallib.library.base.BaseActivity
import com.google.android.material.navigation.NavigationView


class DashBoardActivity : BaseActivity() {


    companion object {
        fun starter(activity: BaseActivity) {
            val intent = Intent(activity, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            activity.startActivity(intent)
        }
    }

    private var binding: ActivityDashBoardBinding? = null
    var viewmodel: DashboardViewModel? = null

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewmodel = ViewModelProviders.of(this, DashboardViewModelFactory(this))
            .get(DashboardViewModel::class.java)
        binding!!.viewModel = viewmodel

        binding!!.lifecycleOwner = this


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_uv_index, R.id.nav_slideshow,
                R.id.nav_select_city, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.getItem(2).setOnMenuItemClickListener {
            it.isChecked
        }

    }

    override fun onResume() {
        super.onResume()
        viewmodel!!.checkIfCitySelected()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getSelectCityFragment(): Boolean {
        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        if (navHostFragment != null) {
            val childFragments = navHostFragment.childFragmentManager.fragments
            childFragments.forEach { fragment ->
                if (fragment is SelectCityFragment) {
                    return false
                }
            }
        }
        return true
    }

    override fun onBackPressed() {

        if (getSelectCityFragment()) {
            if (viewmodel!!.checkIfCitySelected()) {
                showCloseDialog()

            }else{
                navView.menu.getItem(2).setOnMenuItemClickListener {
                    it.isChecked
                }
            }
        } else {
            if (viewmodel!!.checkIfCitySelected()) {
                super.onBackPressed()
            }else{
                showCloseDialog()
            }

        }
    }
     private fun showCloseDialog(){
         MaterialDialog(this).show {
             lifecycleOwner(this@DashBoardActivity)
             title(text = getString(R.string.app_name))
             icon(R.drawable.ic_launcher)
             message(text=getString(R.string.close_app_message))

             positiveButton(text = getString(R.string.yes)){
                 super.onBackPressed()
             }
             negativeButton(text = getString(R.string.no))
         }
     }
}


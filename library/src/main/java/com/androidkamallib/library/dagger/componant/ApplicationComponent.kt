package com.androidkamallib.library.dagger.componant

import com.androidkamallib.library.base.BaseActivity
import com.androidkamallib.library.base.BaseApplication

import javax.inject.Singleton

@Singleton
interface ApplicationComponent {


    fun inject(activity: BaseActivity)
    fun inject(myApplication: BaseApplication)

    /* fun inject(demoApplication: Application)

     val application: Application

     val dataManager: DataManager

     val preferenceHelper: SharedPrefsHelper*/

    /*val dbHelper: DbHelper*/

}
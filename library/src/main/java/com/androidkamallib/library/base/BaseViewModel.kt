package com.androidkamallib.library.base

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidkamallib.library.BuildConfig
import com.androidkamallib.library.R


open class BaseViewModel(var activity: BaseActivity) : ViewModel() {

    var loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun onProgressStart() {
        loadingVisibility.value = true
    }

    fun onProgressFinish() {
        loadingVisibility.value = false

    }

    fun appOnPlayStore(packageName : String){
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (exception: ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    fun openYoutubeLink(youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse( youtubeID))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse( youtubeID))
        try {
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            activity.startActivity(intentBrowser)
        }
    }

    fun shareApp(appId:String=BuildConfig.LIBRARY_PACKAGE_NAME){
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" +
                    appId + "\n\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }


    fun isPackageInstalled(
        packageName: String,
        packageManager: PackageManager = activity.packageManager
    ): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
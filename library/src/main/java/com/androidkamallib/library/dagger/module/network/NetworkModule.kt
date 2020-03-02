package com.androidkamallib.library.dagger.module.network

import android.content.Context
import android.util.Log
import com.androidkamallib.library.BuildConfig
import com.androidkamallib.library.base.BaseVariable.Companion.AUTHRORIZATION
import com.androidkamallib.library.base.BaseVariable.Companion.CACHE_CONTROL
import com.androidkamallib.library.base.BaseVariable.Companion.CURRENT_AUTHRORIZATION
import com.androidkamallib.library.base.BaseVariable.Companion.CURRENT_USER_NAME
import com.androidkamallib.library.base.BaseVariable.Companion.MINUTES_TO_REFRESH_CACHE
import com.androidkamallib.library.base.BaseVariable.Companion.USER_NAME
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


@Module
abstract class NetworkModule(val context: Context, val baseURL: String) {

    private val CLASSTAG: String = NetworkModule::class.java.simpleName

    /* @Provides
     @Singleton
     fun getApiInterface(retroFit: Retrofit): APIInterface? {
         return retroFit.create(APIInterface::class.java)
     }*/

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpLogging =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    CLASSTAG,
                    message
                )
            })
        httpLogging.level = setLoggingLevel()



        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLogging)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(provideHeader())
            .addNetworkInterceptor(provideNetworkInterceptor())
            .cache(getCacheProvider())
            .build()
    }

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * This function will enable application to user caching for API calling
     * create file in cached directory
     * @return Cache object
     */
    private fun getCacheProvider(): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
        } catch (ex: Exception) {
            //Timber.e(ex)
        }
        return cache
    }

    /**
     * @return Entire request if application is in debug mode else return NONE
     */
    private fun setLoggingLevel(): HttpLoggingInterceptor.Level? {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    /**
     * This function will intercept network calls so that we will get significant improvement in
     * API calling performance
     * @return
     */
    private fun provideNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(MINUTES_TO_REFRESH_CACHE, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    /**
     *
     * @return
     */
    /* private fun provideOfflineCachingInterceptor(): Interceptor? {
         return Interceptor { chain ->
             var request = chain.request()
             if (Utility.isNetworkAvailable(App.getApplication())) {
                 val cacheControl = CacheControl.Builder()
                     .maxStale(7, TimeUnit.DAYS)
                     .build()
                 request = request.newBuilder()
                     .cacheControl(cacheControl)
                     .build()
             }
             chain.proceed(request)
         }
     }*/
    @Provides
    fun provideHeader(): Interceptor{
        return Interceptor { chain ->
            val response: Response = chain.proceed(chain.request())

            response.newBuilder()
                .header(USER_NAME,CURRENT_USER_NAME )
                .header(AUTHRORIZATION,CURRENT_AUTHRORIZATION )
                .build()
        }
    }

}
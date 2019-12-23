package com.androidkamallib.libapplication.di.appModule


import android.content.Context
import androidx.room.Room
import com.androidkamallib.libapplication.data.sqilte.WeatherDatabase
import com.androidkamallib.libapplication.data.sqilte.dao.TodayWeatherDao
import com.androidkamallib.library.dagger.annotaions.ApplicationContext
import com.androidkamallib.library.dagger.annotaions.DatabaseInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule(@ApplicationContext context: Context, private val dbName: String) {
    @ApplicationContext
    val mContext: Context = context

    @Singleton
    @Provides
    fun provideDatabase(): WeatherDatabase {
        return Room.databaseBuilder<WeatherDatabase>(
            mContext,
            WeatherDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return dbName
    }

    @Singleton
    @Provides
    fun provideTodayWeatherDao(db: WeatherDatabase): TodayWeatherDao {
        return db.todayWeatherDao()!!
    }

    /*@Singleton
    @Provides
    fun provideProjectDao(db: WeatherDatabase): ProjectDao {
        return db.projectDao()
    }*/

}

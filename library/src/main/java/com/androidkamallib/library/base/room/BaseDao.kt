package com.androidkamallib.library.base.room

import androidx.room.*


@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(t: T): Long

    @Update
    abstract fun update(t: T)

    @Delete
    abstract fun delete(t: T)
}
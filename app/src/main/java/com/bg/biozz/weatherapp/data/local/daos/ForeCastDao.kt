package com.bg.biozz.weatherapp.data.local.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.Single

@Dao
interface ForeCastDao {
    @Query("SELECT * FROM ${ConstantUtils.TABLE_FORECAST} WHERE ${ConstantUtils.KEY_NAME} = :cityName")
    fun getForeCast(cityName: String): Single<ForeCastViewModel>

    @Insert
    fun insertForeCast(foreCastViewModel: ForeCastViewModel)

    @Update
    fun updateForeCast(foreCastViewModel: ForeCastViewModel)
}
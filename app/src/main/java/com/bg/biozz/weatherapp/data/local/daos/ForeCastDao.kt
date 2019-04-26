package com.bg.biozz.weatherapp.data.local.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.ForeCast
import io.reactivex.Single

@Dao
interface ForeCastDao {
    @Query("SELECT * FROM ${ConstantUtils.TABLE_FORECAST} WHERE ${ConstantUtils.KEY_NAME} = :cityName")
    fun getForeCast(cityName: String): Single<ForeCast>
}
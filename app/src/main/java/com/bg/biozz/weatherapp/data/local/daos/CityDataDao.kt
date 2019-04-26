package com.bg.biozz.weatherapp.data.local.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityData
import io.reactivex.Single

@Dao
interface CityDataDao {
    @Query("SELECT * FROM ${ConstantUtils.TABLE_CITYS}")
    fun getCitiesList(): List<CityData>

    @Query("SELECT * FROM ${ConstantUtils.TABLE_CITYS} WHERE ${ConstantUtils.KEY_NAME} = :cityName")
    fun getCityData(cityName: String): Single<CityData>
}
package com.bg.biozz.weatherapp.data.local.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import io.reactivex.Single

@Dao
interface CityDataDao {
    @Query("SELECT * FROM ${ConstantUtils.TABLE_CITYS}")
    fun getCitiesList(): Single<List<CityViewModel>>

    @Query("SELECT * FROM ${ConstantUtils.TABLE_CITYS} WHERE ${ConstantUtils.KEY_DEFAULT_CITY} = 1")
    fun getDefaultCity(): Single<CityViewModel>

    @Query("SELECT * FROM ${ConstantUtils.TABLE_CITYS} WHERE ${ConstantUtils.KEY_NAME} = :cityName")
    fun getCityData(cityName: String): Single<CityViewModel>

    @Query("UPDATE ${ConstantUtils.TABLE_CITYS} SET ${ConstantUtils.KEY_DEFAULT_CITY} = 1 WHERE ${ConstantUtils.KEY_NAME} = :cityName")
    fun setDefaultCity(cityName: String)

    @Query("UPDATE ${ConstantUtils.TABLE_CITYS} SET ${ConstantUtils.KEY_DEFAULT_CITY} = 0 WHERE ${ConstantUtils.KEY_DEFAULT_CITY} = 1 AND NOT ${ConstantUtils.KEY_NAME} = :cityName")
    fun clearDefaultCity(cityName: String)

    @Insert
    fun insertCityData(cityViewModel: CityViewModel)

    @Update
    fun updateCityData(cityViewModel: CityViewModel)
}
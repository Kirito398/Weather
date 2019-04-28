package com.bg.biozz.weatherapp.domain.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.bg.biozz.weatherapp.data.utils.ConstantUtils

@Entity(tableName = ConstantUtils.TABLE_CITYS)
data class CityViewModel(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = ConstantUtils.KEY_NAME)
        val cityName: String,
        @ColumnInfo(name = ConstantUtils.KEY_WEATHER)
        val weather: String,
        @ColumnInfo(name = ConstantUtils.KEY_TEMP_MIN_MAX)
        val tempMinMax: String,
        @ColumnInfo(name = ConstantUtils.KEY_ICON)
        val icon: String,
        @ColumnInfo(name = ConstantUtils.KEY_TEMP)
        val temp: String,
        @ColumnInfo(name = ConstantUtils.KEY_PRESSURE)
        val pressure: String,
        @ColumnInfo(name = ConstantUtils.KEY_HUMIDITY)
        val humidity: String,
        @ColumnInfo(name = ConstantUtils.KEY_DESCRIPTION)
        val description: String,
        @ColumnInfo(name = ConstantUtils.KEY_WIND_SPEED)
        val windSpeed: String,
        @ColumnInfo(name = ConstantUtils.KEY_DT)
        val dt: String
){
        @ColumnInfo(name = ConstantUtils.KEY_DEFAULT_CITY)
        private var isDefault: Int? = null

        fun isDefault(): Int?{
                return isDefault
        }

        fun setIsDefault(isDefault: Int){
                this.isDefault = isDefault
        }
}
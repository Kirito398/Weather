package com.bg.biozz.weatherapp.domain.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.data.utils.DataConverter

@Entity(tableName = ConstantUtils.TABLE_FORECAST)
@TypeConverters(DataConverter::class)
data class ForeCastViewModel(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = ConstantUtils.KEY_NAME)
        val cityName: String,

        @ColumnInfo(name = ConstantUtils.KEY_DAYS_OF_WEEK)
        val daysOfWeek: List<String>,

        @ColumnInfo(name = ConstantUtils.KEY_ICON)
        val icon: List<String>,

        @ColumnInfo(name = ConstantUtils.KEY_TEMP)
        val temp: List<String>
){
    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ConstantUtils.KEY_ID)
    private var ID:Long? = null

    fun getID(): Long?{
        return ID
    }

    fun setID(id: Long){
        ID = id
    }*/
}
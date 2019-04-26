package com.bg.biozz.weatherapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.bg.biozz.weatherapp.data.local.daos.CityDataDao
import com.bg.biozz.weatherapp.data.local.daos.ForeCastDao
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast

@Database(entities = [CityData::class, ForeCast::class], version = ConstantUtils.ROOM_DATABASE_VERSION)
abstract class LocalRoomDB : RoomDatabase() {
    abstract fun cityDataDao(): CityDataDao
    abstract fun foreCastDao(): ForeCastDao

    companion object {
        var INSTANCE: LocalRoomDB? = null

        fun getClient(context: Context): LocalRoomDB{
            if(INSTANCE == null){
                synchronized(LocalRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context, LocalRoomDB::class.java, ConstantUtils.DATABASE_NAME).build()
                }
            }
            return INSTANCE!!
        }
    }
}

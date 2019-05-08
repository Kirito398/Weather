package com.bg.biozz.weatherapp.data.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.bg.biozz.weatherapp.data.local.daos.CityDataDao
import com.bg.biozz.weatherapp.data.local.daos.ForeCastDao
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import org.json.JSONObject

@Database(entities = [CityViewModel::class, ForeCastViewModel::class], version = ConstantUtils.ROOM_DATABASE_VERSION)
abstract class LocalRoomDB : RoomDatabase() {
    abstract fun cityDataDao(): CityDataDao
    abstract fun foreCastDao(): ForeCastDao

    companion object {
        var INSTANCE: LocalRoomDB? = null

        fun getClient(context: Context): LocalRoomDB{
            if(INSTANCE == null){
                synchronized(LocalRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context, LocalRoomDB::class.java, ConstantUtils.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(CALLBACK)
                            .build()
                }
            }
            return INSTANCE!!
        }

        private val CALLBACK = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                addDefaultCity(db)
                Log.d("MainPresenterImpl", "Callback end")
            }
        }

        fun addDefaultCity(db: SupportSQLiteDatabase){
            Log.d("MainPresenterImpl", "Add city start")

            var id: Long = 1
            for(city in ConstantUtils.DEFAULT_CITIES_LIST){
                db.execSQL("INSERT INTO ${ConstantUtils.TABLE_CITYS} VALUES (" +
                        "0, " +
                        "'$city', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}', " +
                        "'${ConstantUtils.NA}'" +
                        ");")
                id++
            }

            db.execSQL("UPDATE ${ConstantUtils.TABLE_CITYS} SET ${ConstantUtils.KEY_DEFAULT_CITY} = 1 WHERE ${ConstantUtils.KEY_NAME} = '${ConstantUtils.DEFAULT_CITIES_LIST[0]}';")

            val json = JSONObject()
            json.put("day 1", ConstantUtils.NA)
            json.put("day 2", ConstantUtils.NA)
            json.put("day 3", ConstantUtils.NA)
            json.put("day 4", ConstantUtils.NA)
            json.put("day 5", ConstantUtils.NA)

            for(city in ConstantUtils.DEFAULT_CITIES_LIST){
                db.execSQL("INSERT INTO ${ConstantUtils.TABLE_FORECAST} VALUES (" +
                        "'$city', " +
                        "'$json', " +
                        "'$json', " +
                        "'$json'" +
                        ")")
            }
        }
    }
}

package com.bg.biozz.weatherapp.data.utils

import android.arch.persistence.room.TypeConverter
import android.util.Log
import org.json.JSONObject

class DataConverter{
    @TypeConverter
    fun listStringToString(data: List<String>): String {
        val json = JSONObject()
        json.put("day 1", data[0])
        json.put("day 2", data[1])
        json.put("day 3", data[2])
        json.put("day 4", data[3])
        json.put("day 5", data[4])
        Log.d("Converter", "StringToJSON: $json")
        return json.toString()
    }

    @TypeConverter
    fun stringToListString(data: String): List<String>{
        val json = JSONObject(data)
        return mutableListOf(
                json.getString("day 1"),
                json.getString("day 2"),
                json.getString("day 3"),
                json.getString("day 4"),
                json.getString("day 5")
        )
    }
}
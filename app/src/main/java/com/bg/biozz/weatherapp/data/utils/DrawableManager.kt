package com.bg.biozz.weatherapp.data.utils

import android.content.Context
import com.bg.biozz.weatherapp.R

class DrawableManager {
    fun getIdDrawable(icon: String): Int{
        return when(icon){
            "01d" -> R.drawable.ic_01d
            "01n" -> R.drawable.ic_01n
            "02d" -> R.drawable.ic_02d
            "02n" -> R.drawable.ic_02n
            "03d" -> R.drawable.ic_03d
            "03n" -> R.drawable.ic_03n
            "04d" -> R.drawable.ic_04n
            "04n" -> R.drawable.ic_04n
            "09d" -> R.drawable.ic_09d
            "09n" -> R.drawable.ic_09n
            "10d" -> R.drawable.ic_10n
            "10n" -> R.drawable.ic_10n
            "11d" -> R.drawable.ic_11d
            "11n" -> R.drawable.ic_11n
            "13d" -> R.drawable.ic_13d
            "13n" -> R.drawable.ic_13n
            "50d" -> R.drawable.ic_50n
            "50n" -> R.drawable.ic_50n
            else -> R.drawable.ic_11d
            //R.drawable.ic_na
        }
    }

    fun convertDPtoPX(dp: Int, ctx: Context): Int{
        val density = ctx.resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
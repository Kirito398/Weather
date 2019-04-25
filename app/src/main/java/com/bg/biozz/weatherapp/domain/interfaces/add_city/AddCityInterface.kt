package com.bg.biozz.weatherapp.domain.interfaces.add_city

interface AddCityInterface {
    interface View{
        fun onAdded()
        fun showIncorrectCityName(cityName: String)
        fun onError(msg: String)
    }
}
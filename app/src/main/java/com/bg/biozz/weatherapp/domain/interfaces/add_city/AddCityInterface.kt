package com.bg.biozz.weatherapp.domain.interfaces.add_city

interface AddCityInterface {
    interface View{
        fun onAdded()
        fun onError(msg: String)
    }
}
package com.bg.biozz.weatherapp.domain.interfaces.select_city

import com.bg.biozz.weatherapp.domain.models.CityViewModel

interface SelectCityInterface {
    interface View{
        fun addCityOnTheList(cityData: CityViewModel)
        fun onError(msg: String)
        fun showProgressBar(show: Boolean)
        fun cleanCityList()
    }
}
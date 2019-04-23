package com.bg.biozz.weatherapp.domain.interactors

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.Single

class MainInteractorImpl(private val mainRepository: MainInterface.Repository) : MainInterface.Interactor {
    private val TAG = "MainInteractorImpl"

    override fun getCityData(cityName: String): Single<CityData> {
        return mainRepository.getCityData(cityName)
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return mainRepository.getForeCast(cityName)
    }

    override fun getCityDataFromLocalDB(cityName: String): CityViewModel {
        return mainRepository.getCityDataFromLocalDB(cityName)
    }

    override fun getForeCastFromLocalDB(cityName: String): ForeCastViewModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDefaultCity(): String {
        return mainRepository.getDefaultCity()
    }


    override fun setDefaultCity(cityName: String) {
        mainRepository.setDefaultCity(cityName)
    }

    override fun getDefaultCitiesList(): List<String> {
        return mainRepository.getCitiesList()
    }

    override fun addNewCity(cityData: CityData) {
        val cityName = cityData.name

        if(!isAdded(cityName)){
            Log.d(TAG, "City $cityName added!")
            mainRepository.addCityIntoDB(cityData)
        }else {
            Log.d(TAG, "City $cityName has been already added!")
        }

        Log.d(TAG, "Set default city: $cityName")
        mainRepository.setDefaultCity(cityName)
    }

    private fun isAdded(cityName: String): Boolean{
        val citiesList = mainRepository.getCitiesList()

        for(str in citiesList){
            if(str == cityName){
                return true
            }
        }
        return false
    }
}
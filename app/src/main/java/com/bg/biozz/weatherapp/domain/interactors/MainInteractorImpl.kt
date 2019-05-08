package com.bg.biozz.weatherapp.domain.interactors

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

    override fun getCityDataFromLocalDB(cityName: String): Single<CityViewModel> {
        return mainRepository.getCityDataFromLocalDB(cityName)
    }

    override fun getForeCastFromLocalDB(cityName: String): Single<ForeCastViewModel> {
        return mainRepository.getForeCastFromLocalDB(cityName)
    }

    override fun getDefaultCity(): Single<CityViewModel> {
        return mainRepository.getDefaultCity()
    }

    override fun setDefaultCity(cityName: String): Single<Unit> {
        return mainRepository.setDefaultCity(cityName)
    }

    override fun getDefaultCitiesList(): Single<List<CityViewModel>> {
        return mainRepository.getCitiesList()
    }

    override fun updateCityDataInLocalDB(cityViewModel: CityViewModel) {
        mainRepository.updateCityDataInLocalDB(cityViewModel)
    }

    override fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel) {
        mainRepository.updateForeCastInLocalDB(foreCastViewModel)
    }

    override fun addNewCity(cityViewModel: CityViewModel) : Single<Unit> {
        return mainRepository.addCityIntoDB(cityViewModel)
    }

    override fun insertNAForeCastInLocalDB(cityName: String) {
        mainRepository.insertNAForeCastInLocalDB(cityName)
    }

    override fun clearDefaultCity(cityName: String): Single<Unit> {
        return mainRepository.clearDefaultCity(cityName)
    }
}
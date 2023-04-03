package com.example.trueclub.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trueclub.Utils.NetworkResult
import com.example.trueclub.data.Repository
import com.example.trueclub.models.IndividualDetials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val repository:Repository,application: Application):AndroidViewModel(application){
    var detialsResponse: MutableLiveData<NetworkResult<IndividualDetials>> = MutableLiveData()

    fun getDetails(queries: Map<String, String>) = viewModelScope.launch {
        getDetailsSafeCall(queries)
    }

    private suspend fun getDetailsSafeCall(queries: Map<String, String>) {
        detialsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getdetails(queries)
                detialsResponse.value = handleWeatherResponse(response)
            }catch (e: Exception) {
                detialsResponse.value = NetworkResult.Error("Weather error not found.")
            }
        } else {
            detialsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleWeatherResponse(response: Response<IndividualDetials>): NetworkResult<IndividualDetials> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                if(foodRecipes != null) {
                    return NetworkResult.Success(foodRecipes)
                } else {
                    return NetworkResult.Error("No data found.")
                }
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}
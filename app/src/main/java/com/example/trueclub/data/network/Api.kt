package com.example.trueclub.data.network

import com.example.trueclub.models.IndividualDetials
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("https://api.nationalize.io/?name=")
    suspend fun getdetails(Name:String):Response<IndividualDetials>
}
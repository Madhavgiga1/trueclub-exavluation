package com.example.trueclub.data.network

import com.example.trueclub.models.IndividualDetials
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("https://api.nationalize.io/?")
    suspend fun getdetails(@QueryMap queries: Map<String, String>):Response<IndividualDetials>
}
package com.example.trueclub.data

import com.example.trueclub.data.network.Api
import com.example.trueclub.models.IndividualDetials
import retrofit2.Response
import retrofit2.http.QueryMap
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: Api) {

    suspend fun getdetails(@QueryMap queries: Map<String, String>):Response<IndividualDetials>{
        return api.getdetails(queries)
    }

}
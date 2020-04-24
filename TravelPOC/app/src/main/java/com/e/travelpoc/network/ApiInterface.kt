package com.e.travelpoc.network

import com.e.travelpoc.models.EmployeeListResponse
import retrofit2.Call
import retrofit2.http.GET


public interface ApiInterface {

    @GET("employees")
    fun getEmployeeList(): Call<EmployeeListResponse>
}


package com.e.travelpoc.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiController {
    companion object {
        val BASE_URL = "https://dummy.restapiexample.com/api/v1/"
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}
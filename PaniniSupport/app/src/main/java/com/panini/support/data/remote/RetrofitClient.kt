package com.panini.support.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // TODO: URL real del backend de Panini cuando exista
    private const val BASE_URL = "https://api.panini-support.example.com/v1/"

    val api: TicketApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TicketApi::class.java)
    }
}
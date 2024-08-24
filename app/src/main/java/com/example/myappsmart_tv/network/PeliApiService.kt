package com.example.myappsmart_tv.network

import com.example.myappsmart_tv.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PeliApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MovieResponse>
}

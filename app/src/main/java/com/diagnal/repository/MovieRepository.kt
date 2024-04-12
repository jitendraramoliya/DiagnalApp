package com.diagnal.repository

import android.content.Context
import com.diagnal.pojo.MovieResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MovieRepository @Inject constructor (@ApplicationContext val appContext: Context) {

    // This repository used to fetch asset json files for movie

    suspend fun getMovieList(filename:String): MovieResponse? {
        return getMovieListFromAsset(filename)
    }

    private fun getMovieListFromAsset(fileName: String): MovieResponse? {
        // Getting movie list from json
        if(fileName.isNotEmpty()) {
            val response: String = appContext.assets.open(fileName).bufferedReader().use { it.readText() }
            return Gson().fromJson(response, MovieResponse::class.java)
        }
        return null
    }

}
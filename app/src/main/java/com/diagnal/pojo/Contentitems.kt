package com.diagnal.pojo

import com.google.gson.annotations.SerializedName

data class Contentitems (

    @SerializedName("content" )
    var content : List<Movie> = listOf()

)
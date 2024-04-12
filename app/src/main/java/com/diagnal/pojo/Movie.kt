package com.diagnal.pojo

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("name") var name: String? = null,
    @SerializedName("poster-image") var poster_image: String? = null

)
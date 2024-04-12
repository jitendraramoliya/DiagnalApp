package com.diagnal.pojo

import com.google.gson.annotations.SerializedName


data class MovieResponse (

  @SerializedName("page" )
  var page : Page? = Page()

)
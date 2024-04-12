package com.diagnal.pojo

import com.diagnal.pojo.Contentitems
import com.google.gson.annotations.SerializedName


data class Page(

    @SerializedName("title")
    var title: String? = null,
    @SerializedName("total-content-items")
    var total_content_items: String? = null,
    @SerializedName("page-num")
    var page_num: String? = null,
    @SerializedName("page-size")
    var page_size: String? = null,
    @SerializedName("content-items")
    var content_items: Contentitems? = Contentitems()

)
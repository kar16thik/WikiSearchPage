package com.example.wikisearch.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikiArticleData (@SerializedName("pageid") @Expose var pageId: Int,
                            @SerializedName("title") @Expose var title: String,
                            @SerializedName("thumbnail") @Expose var imageData: WikiImageData?,
                            @SerializedName("terms") @Expose var descriptionData: WikiDescriptionData,
                            @SerializedName("fullurl") @Expose var fullurl: String,

                            )
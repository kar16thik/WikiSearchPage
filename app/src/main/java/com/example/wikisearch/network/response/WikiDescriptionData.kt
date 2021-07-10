package com.example.wikisearch.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikiDescriptionData (@SerializedName("description")
                                @Expose
                                var description: Array<String>)
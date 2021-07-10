package com.example.wikisearch.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikiImageData (@SerializedName("source")
                          @Expose
                          var source: String)
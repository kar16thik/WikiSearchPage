package com.example.wikisearch.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikiSearchResponse (@SerializedName("query")
                               @Expose
                               var wikiSearchData: WikiSearchResponseData)
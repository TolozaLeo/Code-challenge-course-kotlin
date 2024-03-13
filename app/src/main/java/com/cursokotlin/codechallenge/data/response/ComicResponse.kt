package com.cursokotlin.codechallenge.data.response

import com.squareup.moshi.Json

class Comic(
    @Json(name = "items") var items: List<ComicItem>,
)

class ComicItem(
    @Json(name = "name") var name: String
)

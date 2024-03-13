package com.cursokotlin.codechallenge.data.response

import com.squareup.moshi.Json

class CharactersResponse (
    @Json(name = "data") var data: Data
    )

    data class Data(
        @Json(name = "results") var results: List<Character>
    )

    data class Character(
        @Json(name = "id") var id: Long,
        @Json(name = "name") var name: String,
        @Json(name = "description") var description: String,
        @Json(name = "thumbnail") var thumbnail: Thumbnail,
        @Json(name = "comics") var comics: Comic
    )

    data class Thumbnail(
        @Json(name = "path") val path: String,
        @Json(name = "extension") val extension: String
    )


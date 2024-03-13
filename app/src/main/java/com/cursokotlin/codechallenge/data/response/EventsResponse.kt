package com.cursokotlin.codechallenge.data.response

import com.squareup.moshi.Json

class EventsResponse(
    @Json(name = "data") val data: DataEvent
)

class DataEvent(
    @Json(name = "results") val results: List<Event>
)

class Event(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "comics") val comics: Comic,
    @Json(name = "thumbnail") val thumbnail: Thumbnail
)
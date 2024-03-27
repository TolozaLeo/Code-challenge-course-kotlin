package com.cursokotlin.codechallenge.data.service

import com.cursokotlin.codechallenge.data.response.CharactersResponse
import com.cursokotlin.codechallenge.data.response.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val TIME_STAMP = 1L

const val CHARACTERS_PER_PAGE = 15

const val EVENTS_PER_PAGE = 25
const val START_DATE = "startDate"

interface AvengersApiService {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long = TIME_STAMP,
        @Query("apikey") publicApiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,// a partir de que elemento se pide
        @Query("limit") limit: Int = CHARACTERS_PER_PAGE,
    ) : CharactersResponse

    @GET("/v1/public/events")
    suspend fun getEvents(
        @Query("ts") ts: Long = TIME_STAMP,
        @Query("apikey") publicApiKey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = EVENTS_PER_PAGE,
        @Query("orderBy") orderBy: String = START_DATE
    ) : EventsResponse
}

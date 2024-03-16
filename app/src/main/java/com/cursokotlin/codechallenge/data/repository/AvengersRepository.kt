package com.cursokotlin.codechallenge.data.repository

import com.cursokotlin.codechallenge.data.response.CharactersResponse
import com.cursokotlin.codechallenge.data.response.EventsResponse
import com.cursokotlin.codechallenge.data.service.AvengersApi
import com.cursokotlin.codechallenge.data.Result
import javax.inject.Inject

class AvengersRepository @Inject constructor(
    private val api: AvengersApi
){
    suspend fun getCharacters(page: Int = 0): Result<CharactersResponse> {
        return api.getCharacters(page)
    }

    suspend fun getEvents(page: Int = 0): Result<EventsResponse> {
        return api.getEvents(page)
    }
}
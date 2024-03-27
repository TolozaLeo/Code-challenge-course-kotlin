package com.cursokotlin.codechallenge.data.repository

import com.cursokotlin.codechallenge.data.response.CharactersResponse
import com.cursokotlin.codechallenge.data.response.EventsResponse
import com.cursokotlin.codechallenge.data.service.AvengersApi
import com.cursokotlin.codechallenge.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AvengersRepository @Inject constructor(
    private val api: AvengersApi
){
    suspend fun getCharacters(page: Int): Flow<Result<CharactersResponse>> {
        return api.getCharacters(page)
    }

    suspend fun getEvents(page: Int): Flow<Result<EventsResponse>> {
        return api.getEvents(page)
    }
}
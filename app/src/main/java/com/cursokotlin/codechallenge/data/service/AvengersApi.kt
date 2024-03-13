package com.cursokotlin.codechallenge.data.service

import android.util.Log
import com.cursokotlin.codechallenge.BuildConfig
import com.cursokotlin.codechallenge.data.response.CharactersResponse
import com.cursokotlin.codechallenge.data.service.security.HashKeyBuilder
import javax.inject.Inject
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.internal.getGenericError
import com.cursokotlin.codechallenge.data.response.EventsResponse
import com.cursokotlin.codechallenge.data.service.error.HandleHttpErrors

class AvengersApi @Inject constructor(
    private val api: AvengersApiService,
    private val hashKeyBuilder: HashKeyBuilder,
    private val handleHttpErrors: HandleHttpErrors
){

    private val hashKey: String? = null
        get() = field ?: run {
            hashKeyBuilder.build()
        }

    private val publicKey = BuildConfig.PUBLIC_KEY_AVENGERS_API

    suspend fun getCharacters(page: Int = 0): Result<CharactersResponse> {
        val hash = hashKey
        hash?.let {
            try {
                val result = api.getCharacters(
                    hash = hash,
                    offset = page * CHARACTERS_PER_PAGE,
                    publicApiKey = publicKey
                )
                return Result.Success(result)
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            return handleHttpErrors.handleApiException(Exception(""))
        }
        return Result.Error((getGenericError("There was a problem to generate hash")))
    }

    suspend fun getEvents(page: Int = 0): Result<EventsResponse> {
        val hash = hashKey
        hash?.let {
            try {
                val result = api.getEvents(
                    hash = hash,
                    offset = page * EVENTS_PER_PAGE,
                    publicApiKey = publicKey
                )
                return Result.Success(result)
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            return handleHttpErrors.handleApiException(Exception(""))
        } ?: run {
            return Result.Error((getGenericError("There was a problem to generate hash")))
        }
    }
}
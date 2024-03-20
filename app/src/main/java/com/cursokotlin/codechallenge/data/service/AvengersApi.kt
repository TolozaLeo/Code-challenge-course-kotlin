package com.cursokotlin.codechallenge.data.service

import com.cursokotlin.codechallenge.BuildConfig.PUBLIC_KEY_AVENGERS_API
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.internal.getGenericError
import com.cursokotlin.codechallenge.data.response.CharactersResponse
import com.cursokotlin.codechallenge.data.response.EventsResponse
import com.cursokotlin.codechallenge.data.service.error.HandleHttpErrors
import com.cursokotlin.codechallenge.data.service.security.HashKeyBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AvengersApi @Inject constructor(
    private val api: AvengersApiService,
    private val hashKeyBuilder: HashKeyBuilder,
    private val handleHttpErrors: HandleHttpErrors,
) {
    private val hashKey: String? = null
        get() = field ?: run {
            hashKeyBuilder.build()
        }

    private val publicKey = PUBLIC_KEY_AVENGERS_API

    suspend fun getCharacters(page: Int = 0): Flow<Result<CharactersResponse>> = flow {
        emit(Result.Loading(true))
        val hash = hashKey
        hash?.let {
            try {
                val result = api.getCharacters(
                    hash = hash,
                    offset = page * CHARACTERS_PER_PAGE,
                    publicApiKey = publicKey
                )
                emit(Result.Loading(false))
                emit(Result.Success(result))
                return@flow
            } catch (exception: Exception) {
                handleHttpErrors.handleApiException(exception)
            }
            emit(Result.Loading(false))
            emit(handleHttpErrors.handleApiException(Exception("")))
            return@flow
        }
        emit(Result.Loading(false))
        emit(Result.Error((getGenericError("There was a problem to generate hash"))))
        return@flow
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
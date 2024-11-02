package com.example.freegamesia.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun <ResultType, RequestType> networkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType,
    saveFetchResult: suspend (RequestType) -> Unit,
    onFetchFailed: suspend (Throwable) -> Unit = { },
    shouldFetch: (ResultType) -> Boolean = { true }
): Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error(throwable.message.orEmpty(), it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}

sealed class Resource<T> {

    data class Loading<T>(val result: T?) : Resource<T>()

    data class Success<T>(val result: T) : Resource<T>()

    data class Error<T>(val msgError: String, val result: T? = null) : Resource<T>()
}
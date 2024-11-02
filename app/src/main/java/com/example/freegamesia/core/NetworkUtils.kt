package com.example.freegamesia.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) = flow {

    // Obtener datos locales una sola vez y reutilizarlos
    val localFlow = query()
    val localData = localFlow.firstOrNull()
    emit(Resource.Loading(localData))

    if (localData == null || shouldFetch(localData)) {
        try {
            saveFetchResult(fetch())
            // Reutilizar el flow con map para mejor rendimiento
            localFlow
                .map { Resource.Success(it) }
                .collect { emit(it) }
        } catch (e: Exception) {
            onFetchFailed(e)
            localFlow
                .map { Resource.Error(e.message.orEmpty(), it) }
                .collect { emit(it) }
        }
    } else {
        localFlow
            .map { Resource.Success(it) }
            .collect { emit(it) }
    }
}

sealed class Resource<T> {

    data class Loading<T>(val result: T?) : Resource<T>()

    data class Success<T>(val result: T) : Resource<T>()

    data class Error<T>(val msgError: String, val result: T? = null) : Resource<T>()
}
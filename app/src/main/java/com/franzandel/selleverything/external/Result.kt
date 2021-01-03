package com.franzandel.selleverything.external

/**
 * Created by Franz Andel on 03/01/21.
 * Android Engineer
 */

sealed class Result<out T> {
    data class Success<out S>(val data: S) : Result<S>()
    data class Error(val error: Exception, val errorCode: Int = -1) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
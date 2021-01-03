package com.franzandel.selleverything.external

/**
 * Created by Franz Andel on 03/01/21.
 * Android Engineer
 */

suspend fun <T> suspendTryCatch(
    codeBlock: suspend () -> Result<T>
): Result<T> = try {
    codeBlock.invoke()
} catch (e: Exception) {
    Result.Error(e)
}
package com.sean.jikananimelist.util

import com.sean.jikananimelist.repository.ApiOperation

object ApiUtil {
    inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch(e: Exception) {
            ApiOperation.Failure(exception = e)
        }
    }
}
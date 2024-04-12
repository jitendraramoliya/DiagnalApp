package com.diagnal.utils


sealed class AssetResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : AssetResult<T>(data)
    class Error<T>(message: String, data: T? = null) : AssetResult<T>(data, message)
    class Loading<T> : AssetResult<T>()
}

package com.canwar.rawgvideogames.network.api

class Resource<T> private constructor(
    val status: Status,
    val data: T?,
    val resourceErrorMessage: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(resourceError: String?): Resource<T> {
            return Resource(Status.ERROR, null, resourceError)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}
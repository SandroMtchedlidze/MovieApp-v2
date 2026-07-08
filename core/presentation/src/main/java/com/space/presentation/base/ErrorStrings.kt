package com.space.presentation.base

import com.space.networking.network.NetworkError
import com.space.presentation.R

fun getErrorStrings(error: NetworkError): Int {
    return when (error) {
        NetworkError.NO_INTERNET -> R.string.error_description_no_internet
        NetworkError.SERVER_UNREACHABLE -> R.string.error_description_server_unreachable
        NetworkError.UNAUTHORIZED -> R.string.error_description_unauthorized
        NetworkError.NOT_FOUND -> R.string.error_description_not_found
        NetworkError.EMPTY_RESPONSE -> R.string.error_description_empty
        NetworkError.UNKNOWN -> R.string.error_description_unknown
    }
}
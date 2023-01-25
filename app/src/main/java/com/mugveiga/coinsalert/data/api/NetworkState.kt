package com.mugveiga.coinsalert.data.api

enum class Status { RUNNING, SUCCESS, FAILED }

class NetworkState(val status: Status, val msg: String) {

    companion object {
        private const val defaultError = "Something went wrong"

        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, defaultError)

        fun customError(msg: String?): NetworkState {
            return NetworkState(Status.FAILED, msg ?: defaultError)
        }
    }


}

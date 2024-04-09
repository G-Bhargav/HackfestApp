package com.iitism.hackfestapp.retrofit

import okhttp3.ResponseBody

sealed class Resource <out T>{
    abstract val value: Any

    data class Success<out T>(val value: T):Resource<T>()
    data class  Failure(
        val isNetworkError:Boolean?,
        val errorCode:Int?,
        val errorBody:ResponseBody?
    ):Resource<Nothing>()
}
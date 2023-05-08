package com.rahul.task

data class LogResponseModel(
    val data: List<Data>
)

data class Data(
        val id: Int,
        val timestamp: String,
        val actionType: String
    )

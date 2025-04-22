package com.imarkoff.hotelbooking.api.dto

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

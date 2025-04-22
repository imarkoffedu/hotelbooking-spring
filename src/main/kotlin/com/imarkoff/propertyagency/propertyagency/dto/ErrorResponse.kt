package com.imarkoff.propertyagency.propertyagency.dto

data class ErrorResponse(
    val status: Int,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

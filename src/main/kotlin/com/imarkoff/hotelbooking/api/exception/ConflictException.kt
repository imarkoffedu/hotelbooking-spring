package com.imarkoff.hotelbooking.api.exception

class ConflictException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
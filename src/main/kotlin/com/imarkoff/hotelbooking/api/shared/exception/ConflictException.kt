package com.imarkoff.hotelbooking.api.shared.exception

class ConflictException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
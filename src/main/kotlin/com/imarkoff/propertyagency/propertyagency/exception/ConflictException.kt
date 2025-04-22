package com.imarkoff.propertyagency.propertyagency.exception

class ConflictException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
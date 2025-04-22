package com.imarkoff.propertyagency.propertyagency.type.`typealias`

import java.util.*

typealias UUIDString = String

/**
 * Extension function to convert a UUIDString to a UUID.
 * @throws IllegalArgumentException if the UUIDString is not a valid UUID.
 */
fun UUIDString.toUUID(): UUID {
    return try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid UUID string: $this", e)
    }
}
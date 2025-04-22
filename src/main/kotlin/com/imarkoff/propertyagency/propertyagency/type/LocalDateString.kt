package com.imarkoff.propertyagency.propertyagency.type

import java.time.LocalDate
import java.time.format.DateTimeParseException

typealias LocalDateString = String

/**
 * Represents a date in the format "yyyy-MM-dd".
 * This type alias is used to ensure that date strings are consistently formatted
 * throughout the application.
 *
 * @see java.time.LocalDate
 * @return the parsed local date, not null
 * @throws DateTimeParseException if the text cannot be parsed
 */
fun LocalDateString.toLocalDate(): LocalDate {
    return LocalDate.parse(this)
}
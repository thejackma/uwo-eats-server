@file:OptIn(ExperimentalSerializationApi::class)

package uwoEats.util

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.json.Json

fun <T> Json.parseRequest(deserializer: DeserializationStrategy<T>, string: String): T {
    try {
        return Json.decodeFromString(deserializer, string)
    } catch (e: MissingFieldException) {
        throw e.toInputError()
    }
}

private val missingFieldExceptionMessagePattern = Regex("Field '(?<fieldName>.*)' is required, but it was missing")

private fun MissingFieldException.toInputError(): InputError {
    val match = missingFieldExceptionMessagePattern.matchEntire(message!!)
    val fieldName = match!!.groups["fieldName"]!!.value
    return InputError.build(fieldName, "missing")
}

package uwoEats.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class InputErrorResponse(
    val errors: Map<String, List<String>>
)

fun newInputErrorResponse(err: InputError) = newInputErrorResponse(err.errors)

fun newInputErrorResponse(errors: Map<String, List<String>>) = ResponseBuilder.build {
    statusCode = 422
    rawBody = Json.encodeToString(InputErrorResponse.serializer(), InputErrorResponse(errors))
}

class UnauthorizedError : Throwable()

fun newUnauthorizedResponse() = ResponseBuilder.build {
    statusCode = 401
}

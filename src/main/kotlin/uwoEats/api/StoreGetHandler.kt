package uwoEats.api

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.LogManager
import uwoEats.db.getStore
import uwoEats.model.Store
import uwoEats.util.InputError
import uwoEats.util.MyRequestHandler
import uwoEats.util.ResponseBuilder

@Serializable
private class Response(
    val store: Store
)

class StoreGetHandler : MyRequestHandler {
    override fun handleRequestSafely(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val id = input.pathParameters?.get("id")?.toLong() ?: throw InputError.build("id", "not exists")

        val store = getStore(id)

        val response = Response(
            store
        )

        return ResponseBuilder.build {
            statusCode = 200
            rawBody = Json.encodeToString(Response.serializer(), response)
        }
    }

    companion object {
        private val LOG = LogManager.getLogger(StoreGetHandler::class.java)
    }
}

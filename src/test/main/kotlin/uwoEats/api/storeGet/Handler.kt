package uwoEats.api.storeGet

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
    val store: Store,
)

//function to handle request to get store page content
//Lambda helps to package many funcitons including http request (build connection link, send request to function, request forward, scale etc.) to simplify below function
class Handler : MyRequestHandler {
    override fun handleRequestSafely(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val storeId = input.pathParameters?.get("storeId")?.toLong() ?: throw InputError.build("storeId", "not exists") //obtain path parameter {storeId}

        val store = getStore(storeId)

        val response = Response(
            store
        )

        return ResponseBuilder.build {
            statusCode = 200
            rawBody = Json.encodeToString(Response.serializer(), response)
        }
    }

    companion object {
        private val LOG = LogManager.getLogger(Handler::class.java)
    }
}

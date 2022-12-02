package uwoEats.api.orderGet

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.LogManager
import uwoEats.db.getOrder
import uwoEats.model.Order
import uwoEats.util.InputError
import uwoEats.util.MyRequestHandler
import uwoEats.util.ResponseBuilder

@Serializable
private class Response(
    val order: Order,
)

//function to handle request to get order page info
//retrieve store id, order id to get order, and send response back
class Handler : MyRequestHandler {
    override fun handleRequestSafely(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val storeId = input.pathParameters?.get("storeId")?.toLong() ?: throw InputError.build("storeId", "not exists")
        val orderId = input.pathParameters?.get("orderId") ?: throw InputError.build("orderId", "not exists") 

        val order = getOrder(storeId, orderId)

        val response = Response(
            order
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

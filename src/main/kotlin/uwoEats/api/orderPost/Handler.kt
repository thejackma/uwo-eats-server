package uwoEats.api.orderPost

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.apache.logging.log4j.LogManager
import uwoEats.db.getStore
import uwoEats.db.putOrder
import uwoEats.model.Order
import uwoEats.util.InputError
import uwoEats.util.MyRequestHandler
import uwoEats.util.ResponseBuilder
import uwoEats.util.parseRequest
import java.util.*

@Serializable
private class Request(
    val order: Order,
) {
    @Serializable
    class Order(
        var items: Map<Long, Long> = emptyMap(),
    )
}

@Serializable
private class Response(
    val orderId: String,
)

class Handler : MyRequestHandler {
    override fun handleRequestSafely(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        val storeId = input.pathParameters?.get("storeId")?.toLong() ?: throw InputError.build("storeId", "not exists")
        val store = getStore(storeId)

        val request = Json.parseRequest(Request.serializer(), input.body)

        val items = request.order.items.entries.map {
            val item = store.items[it.key]!!
            Order.Item(
                itemId = it.key,
                name = item.name,
                price = item.price,
                category = item.category,
                quantity = it.value,
            )
        }

        val totalPrice = items.sumOf { it.price.toDouble() * it.quantity }.toFloat()

        val orderId = UUID.randomUUID().toString()

        val order = Order(
            storeId,
            orderId = orderId,
            items = items,
            totalPrice = totalPrice,
        )

        putOrder(order)

        val response = Response(
            orderId = orderId,
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

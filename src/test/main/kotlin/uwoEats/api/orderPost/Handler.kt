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

//request selected order info from order map in format: key(itemId): value(item quantity)
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
        val storeId = input.pathParameters?.get("storeId")?.toLong() ?: throw InputError.build("storeId", "not exists") //get storeId, post storeId into store file
        val store = getStore(storeId) 

        val request = Json.parseRequest(Request.serializer(), input.body) //convert input body to json object (detailed order info with name, quantity) for upload

        val items = request.order.items.entries.map { 
            val item = store.items[it.key]!! 
            Order.Item( //create new order with gotten item info
                itemId = it.key,
                name = item.name,
                price = item.price,
                category = item.category,
                quantity = it.value, //from Request
            )
        }

        val totalPrice = items.sumOf { it.price.toDouble() * it.quantity }.toFloat() //calcualte order total price

        val orderId = UUID.randomUUID().toString() //Randomly generate order id

        val order = Order( //create order object
            storeId,
            orderId = orderId,
            items = items,
            storeName = store.name,
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

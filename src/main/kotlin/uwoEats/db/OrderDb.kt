package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest
import uwoEats.model.Order
import uwoEats.util.InputError
import uwoEats.util.key

fun getOrder(storeId: Long, orderId: String): Order {
    return Table.order.getItem(key(storeId, orderId))
        ?: throw InputError.build("storeId and orderId", "not found")
}

fun putOrder(order: Order) {
    val put = TransactPutItemEnhancedRequest.builder(Order::class.java)
        .item(order)
        .conditionExpression(attributeNotExists(Order::storeId.name, Order::orderId.name))
        .build()

    ddbEnhancedClient.transactWriteItems {
        it.addPutItem(Table.order, put)
    }
}

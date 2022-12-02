package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest
import uwoEats.model.Order
import uwoEats.util.InputError
import uwoEats.util.key

//get order item from order table by given storeid and orderid
fun getOrder(storeId: Long, orderId: String): Order {
    return Table.order.getItem(key(storeId, orderId))
        ?: throw InputError.build("storeId and orderId", "not found")
}

//create dynamodb request with received order, call client and write order content into dynamodb
fun putOrder(order: Order) {
    val put = TransactPutItemEnhancedRequest.builder(Order::class.java)
        .item(order)
        .conditionExpression(attributeNotExists(Order::storeId.name, Order::orderId.name)) 
        .build()

    ddbEnhancedClient.transactWriteItems { // use transact write avoid repeated order id
        it.addPutItem(Table.order, put)
    }
}

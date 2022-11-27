package uwoEats.model

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
@Serializable
class Order(
    @get:DynamoDbPartitionKey
    var storeId: Long = 0,
    @get:DynamoDbSortKey
    var orderId: String = "",
    var items: List<Item> = emptyList(),
    var totalPrice: Float = 0.0f,
) {
    @DynamoDbBean
    @Serializable
    class Item(
        var itemId: Long = 0,
        var name: String = "",
        var price: Float = 0.0f,
        var category: String = "",
        var quantity: Long = 0,
    )
}

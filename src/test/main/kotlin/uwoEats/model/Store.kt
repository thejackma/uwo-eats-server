package uwoEats.model

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
@Serializable

//storeId item format in store table
class Store(
    @get:DynamoDbPartitionKey
    var storeId: Long = 0,
    var name: String = "",
    var address: String = "",
    var items: Map<Long, Item> = emptyMap(),
) {
    @DynamoDbBean
    @Serializable
    class Item( 
        var name: String = "",
        var price: Float = 0.0f,
        var category: String = "",
    )
}

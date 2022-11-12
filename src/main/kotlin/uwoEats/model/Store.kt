package uwoEats.model

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
@Serializable
class Store(
    @get:DynamoDbPartitionKey
    var id: Long = 0,
    var name: String = "",
    var address: String = "",
    var categories: List<Category> = emptyList(),
)

@DynamoDbBean
@Serializable
class Category(
    var name: String = "",
    var items: List<Item> = emptyList(),
)

@DynamoDbBean
@Serializable
class Item(
    var id: Long = 0,
    var name: String = "",
    var price: Float = 0.0f,
)

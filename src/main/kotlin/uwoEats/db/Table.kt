package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import uwoEats.model.Order
import uwoEats.model.Store

val STAGE = System.getenv("STAGE") ?: "dev"

object TableName {
    val STORE = makeTableName("store")
    val ORDER = makeTableName("order")

    private fun makeTableName(suffix: String): String {
        return "uwo-eats-server-$STAGE-$suffix"
    }
}

object Table {
    val store = ddbEnhancedClient.table(TableName.STORE, TableSchema.fromBean(Store::class.java))!!
    val order = ddbEnhancedClient.table(TableName.ORDER, TableSchema.fromBean(Order::class.java))!!
}

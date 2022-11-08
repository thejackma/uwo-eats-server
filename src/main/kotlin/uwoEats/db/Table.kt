package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import uwoEats.model.Store

val STAGE = System.getenv("STAGE") ?: "dev"

object TableName {
    val STORE = makeTableName("store")

    private fun makeTableName(suffix: String): String {
        return "uwo-eats-server-$STAGE-$suffix"
    }
}

object Table {
    val store by lazy { ddbEnhancedClient.table(TableName.STORE, TableSchema.fromBean(Store::class.java))!! }
}

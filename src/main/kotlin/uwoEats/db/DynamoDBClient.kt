package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

val ddbClient = DynamoDbClient.create()!!
val ddbEnhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddbClient).build()!!

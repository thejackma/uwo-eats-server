package uwoEats.util

import software.amazon.awssdk.enhanced.dynamodb.Key

fun key(partitionValue: Number): Key = Key.builder().partitionValue(partitionValue).build()

fun key(partitionValue: Number, sortValue: String): Key =
    Key.builder().partitionValue(partitionValue).sortValue(sortValue).build()

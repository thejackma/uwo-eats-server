package uwoEats.db

import software.amazon.awssdk.enhanced.dynamodb.Expression

fun attributeNotExists(attribute: String): Expression {
    return Expression.builder()
        .expression("attribute_not_exists($attribute)")
        .build()
}

fun attributeNotExists(a: String, b: String): Expression {
    return Expression.builder()
        .expression("attribute_not_exists($a) AND attribute_not_exists($b)")
        .build()
}

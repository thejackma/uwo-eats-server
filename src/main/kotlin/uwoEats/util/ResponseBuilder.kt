package uwoEats.util

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.charset.StandardCharsets
import java.util.*

class ResponseBuilder {
    companion object {
        inline fun build(block: ResponseBuilder.() -> Unit) = ResponseBuilder().apply(block).build()
    }

    val LOG: Logger = LogManager.getLogger(ResponseBuilder::class.java)
    val objectMapper: ObjectMapper = ObjectMapper()

    var statusCode: Int = 200
    var rawBody: String? = null
    var headers: Map<String, String>? = emptyMap()
    var objectBody: Any? = null
    var binaryBody: ByteArray? = null
    var base64Encoded: Boolean = false

    fun build(): APIGatewayV2HTTPResponse {
        var body: String? = null

        if (rawBody != null) {
            body = rawBody as String
        } else if (objectBody != null) {
            try {
                body = objectMapper.writeValueAsString(objectBody)
            } catch (e: JsonProcessingException) {
                LOG.error("failed to serialize object", e)
                throw RuntimeException(e)
            }
        } else if (binaryBody != null) {
            body = String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8)
        }

        return APIGatewayV2HTTPResponse().also {
            it.statusCode = statusCode
            it.body = body
            it.headers = headers
            it.isBase64Encoded = base64Encoded
        }
    }
}

package uwoEats.util

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse

interface MyRequestHandler
    : RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    fun handleRequestSafely(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse

    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
        return try {
            handleRequestSafely(input, context)
        } catch (e: InputError) {
            newInputErrorResponse(e)
        } catch (e: UnauthorizedError) {
            newUnauthorizedResponse()
        }
    }
}

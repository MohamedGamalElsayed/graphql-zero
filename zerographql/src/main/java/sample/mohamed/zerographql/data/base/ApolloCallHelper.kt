package sample.mohamed.zerographql.data.base

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloException
import sample.mohamed.zerographql.data.domain.PostsPage
import java.lang.NullPointerException

/**
 * This method encapsulates all of the required mapping for Apollo calls.
 * Having this single source helps to avoid repeating the logic and having more methods to test.
 * Testing this method is enough, no need to test the services as they only call Apollo and use this method to map the response.
 *
 * [T1] is a type that implements [Operation.Data].
 * [T2] is the type of the desired proper response, e.g. [PostsPage].
 * @param apolloCall is a function that returns ApolloResponse usually [ApolloCall.execute()].
 * @param responseMapping is a function that maps from the Apollo's generated response to a proper one.
 * @return [ApolloResult.Success] of the desired proper response type, or [ApolloResult.Error] with the error message.
 */
suspend fun <T1 : Operation.Data, T2> safeApolloRequest(
    apolloCall: suspend () -> ApolloResponse<T1>,
    responseMapping: (T1) -> T2
): ApolloResult<T2> {
    return try {
        val response = apolloCall.invoke()
        if (response.hasErrors())
            throw ApolloException(response.errors!!.joinToString(", ") { it.message })
        else if (response.data == null)
            throw NullPointerException("Null data is received from the server")
        ApolloResult.Success(responseMapping.invoke(response.data!!))
    } catch (throwable: Throwable) {
        when(throwable) {
            is ApolloException -> ApolloResult.Error("Apollo Error: ${throwable.message}")
            is NullPointerException -> ApolloResult.Error("Null Data: ${throwable.message}")
            else -> ApolloResult.Error("Unexpected Error: ${throwable.message}")
        }
    }
}
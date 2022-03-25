package sample.mohamed.zerographql.data.base

/**
 * Base response result from apollo requests
 */
sealed class ApolloResult<out T> {
    data class Success<out T>(val value: T) : ApolloResult<T>()
    data class Error(val error: String) : ApolloResult<Nothing>()
}
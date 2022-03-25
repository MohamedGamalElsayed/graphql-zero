package sample.mohamed.newsfeed.utils

import sample.mohamed.zerographql.data.base.ApolloResult

fun <T> ApolloResult<T>.toViewState(logger: Logger) = when (this) {
    is ApolloResult.Success -> ViewState.Success(value)
    is ApolloResult.Error -> {
        logger.i(error)
        ViewState.Error
    }
}
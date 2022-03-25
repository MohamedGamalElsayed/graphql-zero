package sample.mohamed.zerographql.data.request

import com.apollographql.apollo3.api.Optional
import sample.mohamed.zerographql.type.PageQueryOptions
import sample.mohamed.zerographql.type.PaginateOptions

internal data class PostsQueryPaginationData(val page: Int, val limit: Int)

internal fun PostsQueryPaginationData.toApolloFormat() =
    Optional.Present(
        PageQueryOptions(
            Optional.Present(
                PaginateOptions(Optional.Present(page), Optional.Present(limit))
            )
        )
    )

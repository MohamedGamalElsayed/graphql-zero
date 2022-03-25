package sample.mohamed.newsfeed.features.timeline.posts.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.coroutineScope
import sample.mohamed.newsfeed.features.timeline.posts.network.PostsPagingSource.Companion.POSTS_PAGE_SIZE
import sample.mohamed.zerographql.GraphQLZeroSDK

open class PostsRepository(
    private val graphQLZeroSDK: GraphQLZeroSDK,
    private val postsPagingSource: PostsPagingSource
) {
    open suspend fun getPosts() = coroutineScope {
        Pager(
            config = PagingConfig(
                pageSize = POSTS_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { postsPagingSource }
        ).flow
    }

    open suspend fun getPostDetails(postId: String) =
        graphQLZeroSDK.session().getPostServices().getPostDetails(postId)
}
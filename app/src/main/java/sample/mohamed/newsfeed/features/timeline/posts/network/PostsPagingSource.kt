package sample.mohamed.newsfeed.features.timeline.posts.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import sample.mohamed.newsfeed.utils.Logger
import sample.mohamed.zerographql.GraphQLZeroSDK
import sample.mohamed.zerographql.data.base.ApolloResult
import sample.mohamed.zerographql.data.domain.Post
import sample.mohamed.zerographql.data.domain.PostsPage

class PostsPagingSource(
    private val graphQLZeroSDK: GraphQLZeroSDK,
    private val logger: Logger
)
    : PagingSource<Int, Post>() {

    companion object {
        const val FIRST_PAGE_NUMBER = 0
        const val POSTS_PAGE_SIZE = 15
    }

    override fun getRefreshKey(state: PagingState<Int, Post>) = FIRST_PAGE_NUMBER

    override suspend fun load(params: LoadParams<Int>) = try {
        val page = params.key ?: FIRST_PAGE_NUMBER
        val apolloResult = graphQLZeroSDK.session().getPostServices().getPosts(
            page = page,
            limit = POSTS_PAGE_SIZE
        )
        when (apolloResult) {
            is ApolloResult.Error -> throw Exception(apolloResult.error)
            is ApolloResult.Success -> getLoadResult(apolloResult, page)
        }
    } catch (exception: Exception) {
        logger.i(exception.message ?: exception.toString())
        LoadResult.Error(exception)
    }

    private fun getLoadResult(
        apolloResult: ApolloResult<PostsPage>,
        page: Int
    ): LoadResult<Int, Post> {
        return when(apolloResult) {
            is ApolloResult.Error -> LoadResult.Error(
                Exception(apolloResult.error)
            )
            is ApolloResult.Success -> {
                val posts = apolloResult.value.posts
                if (posts.isEmpty()) LoadResult.Error(Exception("No data is received"))
                else LoadResult.Page(
                    data = posts,
                    prevKey = getPreviousKey(page),
                    nextKey = getNextKey(apolloResult.value.totalCount, page)
                )
            }
        }
    }

    private fun getPreviousKey(page: Int) =
        if (page == FIRST_PAGE_NUMBER) null
        else page - 1

    private fun getNextKey(totalCount: Int, page: Int) =
        if (page * POSTS_PAGE_SIZE >= totalCount) null
        else page + 1
}
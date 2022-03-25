package sample.mohamed.newsfeed.features.timeline.posts.network

import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import sample.mohamed.newsfeed.utils.Logger
import sample.mohamed.zerographql.GraphQLZeroSDK
import sample.mohamed.zerographql.data.base.ApolloResult
import sample.mohamed.zerographql.data.domain.Post
import sample.mohamed.zerographql.data.domain.PostsPage
import sample.mohamed.zerographql.session.Session

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostsPagingSourceTest {

    @Mock
    private lateinit var postServices: Session.PostServices

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var session: Session

    @Mock
    private lateinit var graphQLZeroSDK: GraphQLZeroSDK

    private lateinit var postsPagingSource: PostsPagingSource

    @Before
    fun setup() {
        given(graphQLZeroSDK.session()).willReturn(session)
        given(session.getPostServices()).willReturn(postServices)
        postsPagingSource = PostsPagingSource(graphQLZeroSDK, logger)
    }

    @Test
    fun `posts paging source load - returns Error - when receives Apollo Error`() = runTest {
        given(
            graphQLZeroSDK.session().getPostServices().getPosts(any(), any())
        ).willReturn(ApolloResult.Error("Error"))

        val expectedResult = PagingSource.LoadResult
            .Error<Int, Post>(Exception("Error"))

        val actualResult = postsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertTrue(actualResult is PagingSource.LoadResult.Error)
        assertEquals(
            expectedResult.throwable.message,
            (actualResult as PagingSource.LoadResult.Error).throwable.message
        )
    }

    @Test
    fun `posts paging source load - returns Error - when receives Apollo Success with empty data`() =
        runTest {
            given(
                graphQLZeroSDK.session().getPostServices().getPosts(any(), any())
            ).willReturn(ApolloResult.Success(PostsPage(listOf(), 0)))

            val expectedResult = PagingSource.LoadResult
                .Error<Int, Post>(Exception("No data is received"))

            val actualResult = postsPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )

            assertTrue(actualResult is PagingSource.LoadResult.Error)
            assertEquals(
                expectedResult.throwable.message,
                (actualResult as PagingSource.LoadResult.Error).throwable.message
            )
        }

    @Test
    fun `posts paging source load - loads result - when receives valid Apollo Result`() =
        runTest {
            val posts = listOf(
                Post("1", "title 1", "body"),
                Post("2", "title 2", "body"),
                Post("3", "title 3", "body")
            )

            given(graphQLZeroSDK.session().getPostServices().getPosts(any(), any())).willReturn(
                ApolloResult.Success(PostsPage(posts, 3))
            )

            val expectedResult = PagingSource.LoadResult.Page(
                data = posts,
                prevKey = null,
                nextKey = 1
            )

            val actualResult = postsPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            assertEquals(expectedResult, actualResult)
        }
}
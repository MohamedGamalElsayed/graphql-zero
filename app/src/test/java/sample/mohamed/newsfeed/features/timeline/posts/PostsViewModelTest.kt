package sample.mohamed.newsfeed.features.timeline.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import sample.mohamed.sharedutils.dispatchers.DispatchersTest
import sample.mohamed.newsfeed.features.timeline.posts.network.PostsRepository
import sample.mohamed.newsfeed.utils.Logger
import sample.mohamed.newsfeed.utils.ViewState
import sample.mohamed.zerographql.data.base.ApolloResult
import sample.mohamed.zerographql.data.domain.Post

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var postsRepository: PostsRepository

    @Mock
    lateinit var logger: Logger

    private lateinit var viewModel: PostsViewModel

    @Before
    fun setup() = Dispatchers.setMain(DispatchersTest.main)

    @After
    fun tearDown() = Dispatchers.setMain(DispatchersTest.main)

    @Test
    fun `init view model - calls get posts`() = runTest {
        initValidData()

        verify(postsRepository).getPosts()
    }

    @Test
    fun `init view model - updates posts list - when receives valid data`() = runTest {
        initValidData()

        viewModel.postsPagingData.test().assertHasValue()
        Dispatchers.resetMain()
    }

    @Test
    fun `update posts loading view state - emits Error - when receives empty data`() = runTest {
        initEmptyData()

        viewModel.updatePostsLoadingViewState(LoadState.NotLoading(false), 0)

        val expectedValue = ViewState.Error

        assertEquals(expectedValue, viewModel.postsViewState.test().value())
    }

    @Test
    fun `update posts loading view state - emits Loading - when receives Loading`() = runTest {
        initEmptyData()

        viewModel.updatePostsLoadingViewState(LoadState.Loading, 0)

        val expectedValue = ViewState.Loading

        assertEquals(expectedValue, viewModel.postsViewState.test().value())
    }

    @Test
    fun `update posts loading view state - emits Success - when receives data`() = runTest {
        initValidData()

        viewModel.updatePostsLoadingViewState(LoadState.NotLoading(false), 10)

        val expectedValue = ViewState.Success(Unit)

        assertEquals(expectedValue, viewModel.postsViewState.test().value())
    }

    @Test
    fun `get post details - emits error - when receives error`() = runTest {
        initValidData()
        given(postsRepository.getPostDetails(any()))
            .willReturn(ApolloResult.Error("Error"))
        viewModel.getPostDetails("1")

        val expectedValue = ViewState.Error
        assertEquals(expectedValue, viewModel.postDetailsViewState.test().value())
    }

    @Test
    fun `get post details - emits success - when receives a post`() = runTest {
        initValidData()
        given(postsRepository.getPostDetails(any()))
            .willReturn(ApolloResult.Success(Post("1", "title", "body")))
        viewModel.getPostDetails("1")

        val expectedValue = ViewState.Success(Post("1", "title", "body"))
        assertEquals(expectedValue, viewModel.postDetailsViewState.test().value())
    }

    @Test
    fun `get post details - doesn't call repo - when requests the same post`() = runTest {
        initValidData()
        given(postsRepository.getPostDetails("1"))
            .willReturn(ApolloResult.Success(Post("1", "title", "body")))
        viewModel.getPostDetails("1")
        viewModel.getPostDetails("1")

        verify(postsRepository, times(1)).getPostDetails("1")
    }

    @Test
    fun `get post details - recalls repo - when requests a different post`() = runTest {
        initValidData()
        given(postsRepository.getPostDetails("1"))
            .willReturn(ApolloResult.Success(Post("1", "title", "body")))
        given(postsRepository.getPostDetails("2"))
            .willReturn(ApolloResult.Success(Post("1", "title", "body")))
        viewModel.getPostDetails("1")
        viewModel.getPostDetails("2")

        verify(postsRepository, times(1)).getPostDetails("1")
        verify(postsRepository, times(1)).getPostDetails("2")
    }

    private fun initEmptyData() = runTest {
        given(postsRepository.getPosts()).willReturn(emptyDataFlow)
        viewModel = PostsViewModel(postsRepository, DispatchersTest, logger)
    }

    private fun initValidData() = runTest {
        given(postsRepository.getPosts()).willReturn(validDataFlow)
        viewModel = PostsViewModel(postsRepository, DispatchersTest, logger)
    }

    private val validPagingData =
        PagingData.from(listOf(Post("id", "title", "body")))

    private val validDataFlow =
        MutableStateFlow(validPagingData)

    private val emptyPagingData =
        PagingData.from(listOf(Post("id", "title", "body")))

    private val emptyDataFlow =
        MutableStateFlow(emptyPagingData)
}
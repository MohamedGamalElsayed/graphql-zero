package sample.mohamed.newsfeed.features.timeline.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sample.mohamed.sharedutils.dispatchers.Dispatchers
import sample.mohamed.newsfeed.features.timeline.posts.network.PostsRepository
import sample.mohamed.newsfeed.utils.EspressoIdlingResource
import sample.mohamed.newsfeed.utils.Logger
import sample.mohamed.newsfeed.utils.ViewState
import sample.mohamed.newsfeed.utils.toViewState
import sample.mohamed.zerographql.data.domain.Post

class PostsViewModel(
    private val repository: PostsRepository,
    private val dispatchers: Dispatchers,
    private val logger: Logger
) : ViewModel() {
    private val mutablePostsPagingData = MutableLiveData<PagingData<Post>>()
    val postsPagingData: LiveData<PagingData<Post>> = mutablePostsPagingData

    private val mutablePostsViewState = MutableLiveData<ViewState<Unit>>()
    val postsViewState: LiveData<ViewState<Unit>> = mutablePostsViewState

    private val mutablePostDetailsViewState = MutableLiveData<ViewState<Post>>()
    val postDetailsViewState: LiveData<ViewState<Post>> = mutablePostDetailsViewState

    init {
        getPosts()
    }

    private fun getPosts() = viewModelScope.launch(dispatchers.io) {
        incrementIdlingResource()
        repository.getPosts().cachedIn(viewModelScope).collectLatest { pagingData ->
            mutablePostsPagingData.postValue(pagingData)
        }
    }

    fun updatePostsLoadingViewState(loadState: LoadState, itemCount: Int) {
        mutablePostsViewState.value =
            when {
                loadState is LoadState.Loading -> ViewState.Loading
                itemCount == 0 -> {
                    decrementIdlingResource()
                    ViewState.Error
                }
                else -> {
                    decrementIdlingResource()
                    ViewState.Success(Unit)
                }
            }
    }

    fun getPostDetails(postId: String) = viewModelScope.launch(dispatchers.main) {
        if ((postDetailsViewState.value is ViewState.Success
                && (postDetailsViewState.value as ViewState.Success).data.id == postId)
        ) return@launch

        incrementIdlingResource()

        mutablePostDetailsViewState.postValue(ViewState.Loading)
        withContext(dispatchers.io) {
            mutablePostDetailsViewState.postValue(
                repository.getPostDetails(postId).toViewState(logger)
            )
            decrementIdlingResource()
        }
    }

    private fun decrementIdlingResource() {
        if (!EspressoIdlingResource.idlingResource.isIdleNow)
            EspressoIdlingResource.decrement()
    }

    private fun incrementIdlingResource() = EspressoIdlingResource.increment()

}
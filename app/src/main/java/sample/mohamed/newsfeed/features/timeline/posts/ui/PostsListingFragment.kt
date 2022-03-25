package sample.mohamed.newsfeed.features.timeline.posts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import sample.mohamed.newsfeed.R
import sample.mohamed.newsfeed.databinding.FragmentPostsListingBinding
import sample.mohamed.newsfeed.features.timeline.posts.PostsListingAdapter
import sample.mohamed.newsfeed.features.timeline.posts.PostsViewModel
import sample.mohamed.newsfeed.utils.MessageHelper
import sample.mohamed.newsfeed.utils.ViewState
import sample.mohamed.newsfeed.utils.hide
import sample.mohamed.newsfeed.utils.show

class PostsListingFragment : Fragment() {

    private val viewModel: PostsViewModel by koinNavGraphViewModel(R.id.nav_graph_timeline)
    private var _binding: FragmentPostsListingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        listenToUpdates()
    }

    private fun initViews() {
        val postsListingAdapter = PostsListingAdapter { post ->
            viewModel.getPostDetails(post.id)
            findNavController().navigate(R.id.action_PostsListingFragment_to_PostDetailsFragment)
        }
        with(binding.rvPostsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = postsListingAdapter
        }
    }

    private fun listenToUpdates() {
        val postsListingAdapter = binding.rvPostsList.adapter as PostsListingAdapter
        viewModel.postsPagingData.observe(this, { pagingData ->
            lifecycleScope.launch {
                postsListingAdapter.submitData(pagingData)
            }
        })
        viewModel.postsViewState.observe(this, { viewState ->
            with(binding) {
                if (viewState is ViewState.Success) {
                    pbLoader.hide()
                    rvPostsList.show()
                } else if (viewState is ViewState.Error) {
                    pbLoader.hide()
                    MessageHelper.showMessage(root, getString(R.string.msg_error_default), MessageHelper.Type.ERROR)
                }
            }
        })
        postsListingAdapter.addLoadStateListener { combinedLoadStates ->
            viewModel.updatePostsLoadingViewState(combinedLoadStates.refresh, postsListingAdapter.itemCount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
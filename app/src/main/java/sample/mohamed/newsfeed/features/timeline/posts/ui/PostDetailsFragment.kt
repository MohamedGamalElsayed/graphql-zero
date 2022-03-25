package sample.mohamed.newsfeed.features.timeline.posts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.navigation.koinNavGraphViewModel
import sample.mohamed.newsfeed.R
import sample.mohamed.newsfeed.databinding.FragmentPostDetailsBinding
import sample.mohamed.newsfeed.features.timeline.posts.PostsViewModel
import sample.mohamed.newsfeed.utils.MessageHelper
import sample.mohamed.newsfeed.utils.ViewState
import sample.mohamed.newsfeed.utils.hide
import sample.mohamed.newsfeed.utils.show
import sample.mohamed.zerographql.data.domain.Post

class PostDetailsFragment : Fragment() {

    private val viewModel: PostsViewModel by koinNavGraphViewModel(R.id.nav_graph_timeline)
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToUpdates()
    }

    private fun listenToUpdates() {
        viewModel.postDetailsViewState.observe(this, { viewState ->
            when (viewState) {
                ViewState.Error -> onError()
                ViewState.Loading -> onLoading()
                is ViewState.Success -> onSuccess(viewState.data)
            }
        })
    }

    private fun onError() = with(binding) {
        pbLoader.hide()
        MessageHelper.showMessage(root, getString(R.string.msg_error_default), MessageHelper.Type.ERROR)
    }

    private fun onLoading() = with(binding) {
        pbLoader.show()
        groupContent.hide()
    }

    private fun onSuccess(post: Post) = with(binding) {
        pbLoader.hide()
        groupContent.show()
        tvAuthor.text =
            getString(R.string.post_details_user_name, post.user?.username, post.user?.name)
        tvTitle.text = post.title
        tvBody.text = post.body
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
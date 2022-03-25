package sample.mohamed.newsfeed.features.timeline.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import sample.mohamed.newsfeed.databinding.ItemViewPostBinding
import sample.mohamed.zerographql.data.domain.Post

class PostsListingAdapter(private val onItemClick: (post: Post) -> Unit) :
    PagingDataAdapter<Post, PostsListingAdapter.PostViewHolder>(PostComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        ItemViewPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onItemClick
    )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PostViewHolder(
        private val binding: ItemViewPostBinding,
        private val onItemClick: (post: Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            tvTitle.text = post.title
            tvBody.text = post.body
            itemViewPost.setOnClickListener { onItemClick(post) }
        }
    }
}

object PostComparator : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
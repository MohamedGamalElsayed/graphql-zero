package sample.mohamed.zerographql.data.domain

import sample.mohamed.zerographql.PostsQuery

data class PostsPage(val posts: List<Post>, val totalCount: Int) {

    /**
     * For [internal] usages only
     */
    internal constructor(data: PostsQuery.Data) : this(
        posts = data.posts?.data!!.map { Post(it!!.id!!, it.title!!, it.body!!) },
        totalCount = data.posts.meta!!.totalCount!!
    )
}

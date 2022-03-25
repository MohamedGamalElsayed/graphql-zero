package sample.mohamed.zerographql.data.domain

import sample.mohamed.zerographql.PostDetailsQuery

data class Post(
    val id: String,
    val title: String,
    val body: String,
    val user: User? = null
) {

    /**
     * For [internal] usages only
     */
    internal constructor(data: PostDetailsQuery.Data, postId: String?) : this(
        id = postId!!,
        title = data.post!!.title!!,
        body = data.post.body!!,
        user = User(
            name = data.post.user!!.name!!,
            username = data.post.user.username!!
        )
    )
}

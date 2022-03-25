package sample.mohamed.zerographql.session

import sample.mohamed.zerographql.data.base.ApolloResult
import sample.mohamed.zerographql.data.domain.Post
import sample.mohamed.zerographql.data.domain.PostsPage

interface Session {
    interface PostServices {
        suspend fun getPosts(page: Int, limit: Int): ApolloResult<PostsPage>
        suspend fun getPostDetails(postId: String): ApolloResult<Post>
    }
    fun getPostServices(): PostServices
}
package sample.mohamed.zerographql.service

import com.apollographql.apollo3.ApolloClient
import sample.mohamed.zerographql.PostDetailsQuery
import sample.mohamed.zerographql.PostsQuery
import sample.mohamed.zerographql.data.base.safeApolloRequest
import sample.mohamed.zerographql.data.domain.Post
import sample.mohamed.zerographql.data.domain.PostsPage
import sample.mohamed.zerographql.data.request.PostsQueryPaginationData
import sample.mohamed.zerographql.data.request.toApolloFormat
import sample.mohamed.zerographql.session.Session

class PostServices(private val apolloClient: ApolloClient) : Session.PostServices {

    override suspend fun getPosts(page: Int, limit: Int) = safeApolloRequest(
        {
            apolloClient.query(
                PostsQuery(
                    PostsQueryPaginationData(page, limit).toApolloFormat()
                )
            ).execute()
        },
        { queryData -> PostsPage(queryData) }
    )

    override suspend fun getPostDetails(postId: String) = safeApolloRequest(
        { apolloClient.query(PostDetailsQuery(postId)).execute() },
        { queryData -> Post(queryData, postId) }
    )
}
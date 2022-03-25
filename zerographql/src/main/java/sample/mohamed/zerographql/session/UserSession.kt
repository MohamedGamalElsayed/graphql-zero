package sample.mohamed.zerographql.session

import com.apollographql.apollo3.ApolloClient
import sample.mohamed.zerographql.service.PostServices

class UserSession(private val apolloClient: ApolloClient) : Session {
    override fun getPostServices() = PostServices(apolloClient)
}
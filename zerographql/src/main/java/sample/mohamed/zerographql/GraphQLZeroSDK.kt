package sample.mohamed.zerographql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import sample.mohamed.zerographql.session.Session
import sample.mohamed.zerographql.session.UserSession

open class GraphQLZeroSDK(private val okHttpClient: OkHttpClient, private val baseUrl: String) {

    /**
     * It doesn't have a state, so no leaks will occur
     */
    private var session: Session

    init {
        session = UserSession(getApolloClient())
    }

    /**
     * Easy to stub it to return a Mock Session for testing
     */
    open fun session() = session

    private fun getApolloClient() =
        ApolloClient.Builder().serverUrl(baseUrl).okHttpClient(okHttpClient).build()

}
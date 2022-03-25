package sample.mohamed.zerographql.mocks

import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Query

class MockApolloData(val text: String) : Query.Data

data class MockResponse(val text: String)

fun apolloError(message: String) =
    Error(message, null, null, null, null)


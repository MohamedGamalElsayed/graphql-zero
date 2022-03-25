package sample.mohamed.zerographql.data.base

import com.apollographql.apollo3.api.ApolloResponse.Builder
import com.apollographql.apollo3.api.Operation
import com.benasher44.uuid.Uuid
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import sample.mohamed.zerographql.mocks.MockApolloData
import sample.mohamed.zerographql.mocks.MockResponse
import sample.mohamed.zerographql.mocks.apolloError

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ApolloCallHelperTest {

    @Mock
    private lateinit var operation: Operation<MockApolloData>

    @Test
    fun `call safe Apollo Request - returns error result - when receives error response`() =
        runTest {
            val expectedResponse = ApolloResult.Error("Apollo Error: Error")

            val actualResponse = safeApolloRequest(
                { getErrorResponse() },
                { queryData -> MockResponse(queryData.text) }
            )

            assertEquals(expectedResponse, actualResponse)
        }

    @Test
    fun `call safe Apollo Request - returns error result - when receives null data`() =
        runTest {
            val expectedResponse = ApolloResult.Error("Null Data: Null data is received from the server")

            val actualResponse = safeApolloRequest(
                { getNullResponse() },
                { queryData -> MockResponse(queryData.text) }
            )

            assertEquals(expectedResponse, actualResponse)
        }

    @Test
    fun `call safe Apollo Request - returns error result - when unexpected error occurs`() =
        runTest {
            val expectedResponse = ApolloResult.Error("Unexpected Error: Number format exception")

            val actualResponse = safeApolloRequest(
                { getValidResponse() },
                { throw NumberFormatException("Number format exception") }
            )

            assertEquals(expectedResponse, actualResponse)
        }

    @Test
    fun `call safe Apollo Request - returns success result`() =
        runTest {
            val expectedResponse = ApolloResult.Success(MockResponse("text"))

            val actualResponse = safeApolloRequest(
                { getValidResponse() },
                { queryData -> MockResponse(queryData.text) }
            )

            assertEquals(expectedResponse, actualResponse)
        }

    private fun getErrorResponse() = Builder(operation, Uuid.randomUUID(), null)
        .errors(listOf(apolloError("Error")))
        .build()

    private fun getNullResponse() = Builder(operation, Uuid.randomUUID(), null)
        .build()

    private fun getValidResponse() = Builder(operation, Uuid.randomUUID(), MockApolloData("text"))
        .build()
}
package sample.mohamed.newsfeed.di

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import sample.mohamed.newsfeed.BuildConfig
import sample.mohamed.newsfeed.features.timeline.posts.network.PostsPagingSource
import sample.mohamed.newsfeed.features.timeline.posts.network.PostsRepository
import sample.mohamed.newsfeed.utils.Logger
import sample.mohamed.sharedutils.dispatchers.Dispatchers
import sample.mohamed.sharedutils.dispatchers.DispatchersReal
import sample.mohamed.zerographql.GraphQLZeroSDK

object AppModule {
    fun create() = module {
        single<Dispatchers> { DispatchersReal }
        single { Logger() }
        single { GraphQLZeroSDK(getOkHttpClient(), BuildConfig.ZERO_GRAPHQL_URL) }
        factory { PostsPagingSource(get(), get()) }
        // Preferred it to be single as it's used everywhere for now + it doesn't carry any state
        single { PostsRepository(get(), get()) }
    }

    private fun getOkHttpClient() = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor(
                logger = object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.i("OkHttp", message)
                    }
                }
            ).apply { level = HttpLoggingInterceptor.Level.BODY })
        }
    }.build()
}
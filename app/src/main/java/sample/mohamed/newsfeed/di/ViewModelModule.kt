package sample.mohamed.newsfeed.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sample.mohamed.newsfeed.features.timeline.posts.PostsViewModel

object ViewModelModule {
    fun create() = module {
        viewModel { PostsViewModel(get(), get(), get()) }
    }
}
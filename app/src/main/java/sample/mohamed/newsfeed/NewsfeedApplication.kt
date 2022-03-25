package sample.mohamed.newsfeed

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import sample.mohamed.newsfeed.di.AppModule
import sample.mohamed.newsfeed.di.ViewModelModule

class NewsfeedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() = startKoin {
        androidContext(this@NewsfeedApplication)
        modules(listOf(AppModule.create(), ViewModelModule.create()))
    }
}
package sample.mohamed.sharedutils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
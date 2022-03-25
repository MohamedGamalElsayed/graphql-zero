package sample.mohamed.sharedutils.dispatchers

object DispatchersReal : Dispatchers {

    override val main
        get() = kotlinx.coroutines.Dispatchers.Main

    override val default
        get() = kotlinx.coroutines.Dispatchers.Default

    override val io
        get() = kotlinx.coroutines.Dispatchers.IO

    override val unconfined
        get() = kotlinx.coroutines.Dispatchers.Unconfined
}
package sample.mohamed.sharedutils.dispatchers

object DispatchersTest : Dispatchers {

  private var mainLocal = kotlinx.coroutines.Dispatchers.Unconfined
  private var defaultLocal = kotlinx.coroutines.Dispatchers.Unconfined
  private var ioLocal = kotlinx.coroutines.Dispatchers.Unconfined
  private var unconfinedLocal = kotlinx.coroutines.Dispatchers.Unconfined

  override val main @Synchronized get() = mainLocal
  override val default @Synchronized get() = defaultLocal
  override val io @Synchronized get() = ioLocal
  override val unconfined @Synchronized get() = unconfinedLocal

}
package kg.hackathon.template.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseVM<State : Any>(s: State) : ContainerHost<State, SideEffect>, ViewModel() {

    override val container = container<State, SideEffect>(s)

    abstract fun doAction(action: Action)

    open fun launchWithErrorHandling(
        call: suspend (scope: CoroutineScope) -> Unit,
        scope: CoroutineScope? = viewModelScope,
        finally: (suspend () -> Unit)? = null,
        handleError: ((e: Throwable) -> Unit)? = null
    ): Job {
        return scope?.launch {
            try {
                call(scope)
            } catch (e: Throwable) {
                handleError?.invoke(e) ?: this@BaseVM.handleError(e)
            } finally {
                finally?.invoke() ?: hideLoading()
            }
        } ?: Job()
    }

    open fun handleError(e: Throwable) = intent {
        postSideEffect(TextToast(e.message.toString()))
    }

    open fun hideLoading() {}
}
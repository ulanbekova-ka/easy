package kg.hackathon.template.ui.preload

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.base.State
import kg.hackathon.template.data.prefs.UserPrefs
import kg.hackathon.template.ui.preload.data.PreloadSideEffect
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PreloadVM @Inject constructor(
    private val userPrefs: UserPrefs
) : BaseVM<Any>(State.Initialize()) {

    override val container = container<Any, SideEffect>(State.Initialize()) {
        checkUserAndGoToScreen()
    }

    private fun checkUserAndGoToScreen() = intent {
        if (userPrefs.token.isEmpty()) {
            PreloadSideEffect.OpenLoginScreen
        } else {
            PreloadSideEffect.OpenMainScreen
        }.let { effect ->
            postSideEffect(effect)
        }
    }

    override fun doAction(action: Action) {}
}
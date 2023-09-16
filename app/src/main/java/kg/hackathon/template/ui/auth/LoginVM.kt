package kg.hackathon.template.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.data.repository.LoginRepository
import kg.hackathon.template.ui.auth.data.LoginAction
import kg.hackathon.template.ui.auth.data.LoginScreenState
import kg.hackathon.template.ui.auth.data.LoginSideEffect
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val repo: LoginRepository
) : BaseVM<LoginScreenState>(LoginScreenState()) {

    override fun doAction(action: Action) {
        when (action) {
            is LoginAction.OnUsernameChange -> onInputUsernameChanged(action.data)
            is LoginAction.OnPasswordChange -> onInputPasswordChanged(action.data)
            is LoginAction.OnRegisterClick -> openRegisterScreen()
            is LoginAction.OnLoginClick -> login()
        }
    }

    private fun onInputUsernameChanged(data: String?) = intent {
        reduce { state.copy(login = data ?: "") }
    }

    private fun onInputPasswordChanged(data: String?) = intent {
        reduce { state.copy(password = data ?: "") }
    }

    private fun login() = intent {
        showLoading()
        launchWithErrorHandling({
            repo.login(state.login, state.password)
            postSideEffect(LoginSideEffect.OpenMainScreen)
        })
    }

    private fun openRegisterScreen() = intent {
        postSideEffect(LoginSideEffect.OpenRegisterScreen)
    }

    private fun showLoading() = intent {
        reduce { state.copy(loading = true) }
    }

    override fun hideLoading() = intent {
        reduce { state.copy(loading = false) }
    }
}
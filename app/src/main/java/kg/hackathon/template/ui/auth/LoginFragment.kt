package kg.hackathon.template.ui.auth

import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.R
import kg.hackathon.template.activity.AppActivity
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.base.SimpleTextWatcher
import kg.hackathon.template.databinding.FragmentLoginBinding
import kg.hackathon.template.ui.auth.data.LoginAction
import kg.hackathon.template.ui.auth.data.LoginScreenState
import kg.hackathon.template.ui.auth.data.LoginSideEffect
import kg.hackathon.template.utils.navigateTo

@AndroidEntryPoint
class LoginFragment : BaseMviFragment<LoginScreenState, FragmentLoginBinding, LoginVM>(
    FragmentLoginBinding::inflate
) {
    override val vm: LoginVM by viewModels()

    override fun initViews() = with(vb) {
        super.initViews()
        (activity as AppActivity).bottomMenuVisibility(false)
        inpUsername.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(LoginAction.OnUsernameChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpPassword.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(LoginAction.OnPasswordChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        btnRegister.run {
            text = getString(R.string.register)
            setOnClickListener {
                vm.doAction(LoginAction.OnRegisterClick)
            }
        }
        btnLogin.run {
            text = getString(R.string.login)
            setOnClickListener {
                vm.doAction(LoginAction.OnLoginClick)
            }
        }
    }

    override suspend fun onRender(state: LoginScreenState) {
        super.onRender(state)
        renderUI(state)
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
        when (sideEffect) {
            is LoginSideEffect.OpenMainScreen -> navigateTo(LoginFragmentDirections.toMain())
            is LoginSideEffect.OpenRegisterScreen -> navigateTo(LoginFragmentDirections.toRegister())
        }
    }

    private fun renderUI(state: LoginScreenState) = with(vb) {
        setupLoadingState(state.loading)
//        inpUsername.run {
//            state.error?.let { setupFieldAsError(it) }
//        }
//        inpPassword.run {
//            state.error?.let { setupFieldAsError(it) }
//        }
        btnLogin.isEnabled = state.isBtnEnabled
    }


    private fun setupLoadingState(loading: Boolean) {
        when (loading) {
            true -> showProgressLoading()
            false -> hideProgressLoading()
        }
    }

    private fun showProgressLoading() = with(vb) {
        progress.isInvisible = false
    }

    private fun hideProgressLoading() = with(vb) {
        progress.isInvisible = true
    }
}
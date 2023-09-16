package kg.hackathon.template.ui.register

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.data.network.model.ClientBody
import kg.hackathon.template.data.network.model.ShopBody
import kg.hackathon.template.data.repository.LoginRepository
import kg.hackathon.template.ui.register.data.RegisterAction
import kg.hackathon.template.ui.register.data.RegisterScreenState
import kg.hackathon.template.ui.register.data.RegisterSideEffect
import kg.hackathon.template.ui.register.data.UserType
import kg.hackathon.template.utils.convertToBase64
import kg.hackathon.template.utils.isInvalidDate
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val repo: LoginRepository
) : BaseVM<RegisterScreenState>(RegisterScreenState()) {

    override fun doAction(action: Action) {
        when (action) {
            is RegisterAction.OnUsernameChange -> onInputUsernameChanged(action.data)
            is RegisterAction.OnPasswordChange -> onInputPasswordChanged(action.data)
            is RegisterAction.OnRepPasswordChange -> onInputPassword2Changed(action.data)
            is RegisterAction.OnBirthdayChange -> onInputBirthdayChanged(action.data)
            is RegisterAction.OnEmailChange -> onInputEmailChanged(action.data)
            is RegisterAction.OnNameChange -> onInputNameChanged(action.data)
            is RegisterAction.OnAddressChange -> onInputAddressChanged(action.data)
            is RegisterAction.OnInstagramChange -> onInputInstagramChanged(action.data)
            is RegisterAction.OnPhoneChange -> onInputPhoneChanged(action.data)
            is RegisterAction.OnPhotoDone -> onPhotoDone(action.photo)
            is RegisterAction.OnRegisterClick -> checkInputs(action.type)
        }
    }

    private fun onInputUsernameChanged(data: String?) = intent {
        reduce { state.copy(username = data ?: "") }
    }

    private fun onInputPasswordChanged(data: String?) = intent {
        reduce { state.copy(password = data ?: "") }
    }

    private fun onInputPassword2Changed(data: String?) = intent {
        reduce { state.copy(password2 = data ?: "") }
    }

    private fun onInputBirthdayChanged(data: String?) = intent {
        reduce { state.copy(birthday = data ?: "") }
    }

    private fun onInputEmailChanged(data: String?) = intent {
        reduce { state.copy(email = data ?: "") }
    }

    private fun onInputNameChanged(data: String?) = intent {
        reduce { state.copy(name = data ?: "") }
    }

    private fun onInputInstagramChanged(data: String?) = intent {
        reduce { state.copy(instagram = data ?: "") }
    }

    private fun onInputAddressChanged(data: String?) = intent {
        reduce { state.copy(address = data ?: "") }
    }

    private fun onInputPhoneChanged(data: String?) = intent {
        reduce { state.copy(phone = data ?: "") }
    }

    private fun onPhotoDone(photo: ByteArray) = intent {
        val convertedImage = convertToBase64(photo)
        reduce { state.copy(photo = convertedImage) }
    }

    private fun checkInputs(type: UserType) = intent {
        val effect = if (state.password.length < 8) RegisterSideEffect.ShowPasswordWeak
        else if (state.password != state.password2) RegisterSideEffect.ShowPasswordsNotSame
        else if (state.birthday.isInvalidDate() && state.birthday.isNotEmpty()) RegisterSideEffect.ShowBirthdayIncorrect
        else null

        if (effect != null) postSideEffect(effect)
        else when (type) {
            UserType.CLIENT -> registerCustomer()
            else -> registerSeller()
        }
    }


    private fun registerCustomer() = intent {
        showLoading()
        launchWithErrorHandling({
            val body = ClientBody(
                login = state.username,
                password = state.password,
                fullName = state.name,
                phone = state.phone,
                profileImageBase64 = state.photo,
                dateOfBirth = state.birthday,
                email = state.email
            )
            repo.registerClient(body)
            postSideEffect(RegisterSideEffect.OpenMainScreen)
        })
    }

    private fun registerSeller() = intent {
        showLoading()
        launchWithErrorHandling({
            val body = ShopBody(
                login = state.username,
                password = state.password,
                name = state.name,
                phone = state.phone,
                profileImageBase64 = state.photo,
                address = state.address,
                instagramAccount = state.instagram
            )
            repo.registerShop(body)
            postSideEffect(RegisterSideEffect.OpenMainScreen)
        })
    }

    private fun showLoading() = intent {
        reduce { state.copy(loading = true) }
    }

    override fun hideLoading() = intent {
        reduce { state.copy(loading = false) }
    }
}
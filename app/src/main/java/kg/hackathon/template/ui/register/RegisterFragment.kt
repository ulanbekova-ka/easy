package kg.hackathon.template.ui.register

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.R
import kg.hackathon.template.base.BaseMviFragment
import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.base.SimpleTextWatcher
import kg.hackathon.template.databinding.FragmentRegisterBinding
import kg.hackathon.template.ui.camera.CameraFragment
import kg.hackathon.template.ui.register.data.RegisterAction
import kg.hackathon.template.ui.register.data.RegisterScreenState
import kg.hackathon.template.ui.register.data.RegisterSideEffect
import kg.hackathon.template.ui.register.data.UserType
import kg.hackathon.template.utils.navigateTo

@AndroidEntryPoint
class RegisterFragment : BaseMviFragment<RegisterScreenState, FragmentRegisterBinding, RegisterVM>(
    FragmentRegisterBinding::inflate
) {
    override val vm: RegisterVM by viewModels()

    private val navArgs: RegisterFragmentArgs by navArgs()

    override fun initViews() = with(vb) {
        super.initViews()
        title.text = getTitle()

        inpUsername.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnUsernameChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpPassword.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnPasswordChange(s.toString())) },
                afterTextChanged = { this.error = null; inpPassword2.error = null }
            )
            addTextChangedListener(textWatcher)
        }
        inpPassword2.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnRepPasswordChange(s.toString())) },
                afterTextChanged = { this.error = null; inpPassword.error = null }
            )
            addTextChangedListener(textWatcher)
        }
        inpPhone.run {
            hint = "0XXXXXXXXX"
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnPhoneChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpName.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnNameChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }

        when(navArgs.type) {
            UserType.CLIENT -> initForCustomer()
            UserType.SHOP -> initForSeller()
        }

        setFragmentResultListener(CameraFragment.PHOTO) { _, bundle ->
            val byteArray = bundle.getByteArray(CameraFragment.PHOTO)
            byteArray?.let { vm.doAction(RegisterAction.OnPhotoDone(it)) }
        }
        btnPhoto.run {
            text = getString(R.string.upload_photo)
            setOnClickListener {
                navigateTo(RegisterFragmentDirections.toCameraFragment())
            }
        }

        btnRegister.setOnClickListener {
            vm.doAction(RegisterAction.OnRegisterClick(navArgs.type))
        }
    }

    private fun initForCustomer() = with(vb) {
        inpNameLayout.hint = getString(R.string.full_name)
        inpBirthdate.run {
            hint = "yyyy-mm-dd"
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnBirthdayChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpEmail.run {
            hint = "sample@gmail.com"
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnEmailChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpAddress.isVisible = false
        inpInstagram.isVisible = false
    }

    private fun initForSeller() = with(vb) {
        inpNameLayout.hint = getString(R.string.name)
        inpBirthdate.isVisible = false
        inpEmail.isVisible = false
        inpAddress.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnAddressChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
        inpInstagram.run {
            val textWatcher = SimpleTextWatcher(
                onTextChanged = { s, _, _, _ -> vm.doAction(RegisterAction.OnInstagramChange(s.toString())) }
            )
            addTextChangedListener(textWatcher)
        }
    }

    private fun getTitle(): String {
        return when(navArgs.type) {
            UserType.CLIENT -> getString(R.string.register_client)
            UserType.SHOP -> getString(R.string.register_shop)
        }
    }

    override suspend fun onRender(state: RegisterScreenState) {
        super.onRender(state)
        renderUI(state)
    }

    override suspend fun onSideEffect(sideEffect: SideEffect) {
        super.onSideEffect(sideEffect)
        when (sideEffect) {
            is RegisterSideEffect.OpenMainScreen -> navigateTo(RegisterFragmentDirections.toMain())
            is RegisterSideEffect.ShowPasswordsNotSame -> showNotSameError()
            is RegisterSideEffect.ShowPasswordWeak ->
                vb.inpPassword.error = getString(R.string.pass_weak)
            is RegisterSideEffect.ShowBirthdayIncorrect ->
                vb.inpBirthdate.error = getString(R.string.birthday_error)
        }
    }

    private fun showNotSameError() = with(vb) {
        inpPassword.error = getString(R.string.password_not_same)
        inpPassword2.error = getString(R.string.password_not_same)
    }

    private fun renderUI(state: RegisterScreenState) = with(vb) {
        setupLoadingState(state.loading)
        btnRegister.isEnabled = state.isBtnEnabled
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
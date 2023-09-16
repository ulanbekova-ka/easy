package kg.hackathon.template.ui.register_type

import dagger.hilt.android.lifecycle.HiltViewModel
import kg.hackathon.template.base.Action
import kg.hackathon.template.base.BaseVM
import kg.hackathon.template.ui.register.data.RegisterScreenState
import kg.hackathon.template.ui.register.data.UserType
import kg.hackathon.template.ui.register_type.data.RegisterTypeAction
import kg.hackathon.template.ui.register_type.data.RegisterTypeSideEffect
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import javax.inject.Inject

@HiltViewModel
class RegisterTypeVM @Inject constructor() : BaseVM<RegisterScreenState>(RegisterScreenState()) {

    override fun doAction(action: Action) {
        when (action) {
            is RegisterTypeAction.OnCustomerClick -> onCustomerClick()
            is RegisterTypeAction.OnSellerClick -> onSellerClick()
        }
    }

    private fun onCustomerClick() = intent {
        postSideEffect(RegisterTypeSideEffect.OpenRegisterScreen(UserType.CLIENT))
    }

    private fun onSellerClick() = intent {
        postSideEffect(RegisterTypeSideEffect.OpenRegisterScreen(UserType.SHOP))
    }
}
package kg.hackathon.template.ui.register_type.data

import kg.hackathon.template.base.SideEffect
import kg.hackathon.template.ui.register.data.UserType

sealed class RegisterTypeSideEffect : SideEffect() {
    class OpenRegisterScreen(val type: UserType) : RegisterTypeSideEffect()
}
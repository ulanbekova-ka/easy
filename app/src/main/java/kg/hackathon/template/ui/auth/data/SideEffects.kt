package kg.hackathon.template.ui.auth.data

import kg.hackathon.template.base.SideEffect

sealed class LoginSideEffect : SideEffect() {
    object OpenMainScreen : LoginSideEffect()
    object OpenRegisterScreen : LoginSideEffect()
}
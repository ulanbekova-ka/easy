package kg.hackathon.template.ui.register.data

import kg.hackathon.template.base.SideEffect

sealed class RegisterSideEffect : SideEffect() {
    object OpenMainScreen : RegisterSideEffect()
    object ShowPasswordsNotSame : RegisterSideEffect()
    object ShowPasswordWeak : RegisterSideEffect()
    object ShowBirthdayIncorrect : RegisterSideEffect()
}
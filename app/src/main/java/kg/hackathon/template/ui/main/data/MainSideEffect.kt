package kg.hackathon.template.ui.main.data

import kg.hackathon.template.base.SideEffect

sealed class MainSideEffect : SideEffect() {
    object NavigateToLoginScreen : MainSideEffect()
}
package kg.hackathon.template.ui.preload.data

import kg.hackathon.template.base.SideEffect

sealed class PreloadSideEffect : SideEffect() {
    object OpenLoginScreen : PreloadSideEffect()
    object OpenMainScreen : PreloadSideEffect()
}
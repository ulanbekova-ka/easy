package kg.hackathon.template.ui.main.data

import kg.hackathon.template.base.State
import kg.hackathon.template.data.network.model.User

data class MainState(
    val user: User? = null,
    val loading: Boolean = false
) : State()
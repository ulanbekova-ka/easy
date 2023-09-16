package kg.hackathon.template.ui.account.data

import kg.hackathon.template.data.network.model.User

data class AccountState(
    val user: User? = null,
    val loading: Boolean = false
)

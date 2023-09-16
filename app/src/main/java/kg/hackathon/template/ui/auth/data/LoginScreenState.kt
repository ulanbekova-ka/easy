package kg.hackathon.template.ui.auth.data

data class LoginScreenState(
    val login: String = "",
    val password: String = "",
    val error: String? = null,
    val loading: Boolean = false
) {
    val isBtnEnabled: Boolean = login.isNotEmpty() && password.isNotEmpty()
}
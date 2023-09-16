package kg.hackathon.template.ui.register.data

data class RegisterScreenState(
    val username: String = "",
    val password: String = "",
    val password2: String = "",
    val name: String = "",
    val phone: String = "",
    val photo: String = "",
    val birthday: String = "",
    val email: String = "",
    val address: String = "",
    val instagram: String = "",
    val loading: Boolean = false
) {
    val isBtnEnabled: Boolean = username.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()
            && phone.isNotEmpty() && ((birthday.isNotEmpty() && email.isNotEmpty()) ||
            (address.isNotEmpty() && instagram.isNotEmpty()))
}

enum class UserType {
    CLIENT, SHOP
}
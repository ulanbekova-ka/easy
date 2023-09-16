package kg.hackathon.template.data.network.model

data class LoginBody(
    val login: String,
    val password: String
)

data class ClientBody(
    val login: String,
    val password: String,
    val fullName: String,
    val phone: String,
    val profileImageBase64: String,
    val dateOfBirth: String,
    val email: String
)

data class ShopBody(
    val login: String,
    val password: String,
    val name: String,
    val phone: String,
    val profileImageBase64: String,
    val address: String,
    val instagramAccount: String
)
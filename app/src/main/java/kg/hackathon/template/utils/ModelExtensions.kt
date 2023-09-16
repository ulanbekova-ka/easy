package kg.hackathon.template.utils

import kg.hackathon.template.data.network.model.User
import kg.hackathon.template.data.network.model.UserResponse

fun UserResponse.convertToUser(): User {
    return User(
        id = id,
        login = login,
        fullName = fullName,
        userType = userType,
        profileImage = profileImage
    )
}
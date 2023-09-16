package kg.hackathon.template.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kg.hackathon.template.ui.register.data.UserType

data class UserResponse(
    @SerializedName("jwtValue")
    val token: String? = null,
    val message: String? = null,
    val id: Long? = null,
    val login: String? = "",
    val fullName: String? = "",
    val userType: UserType? = null,
    val profileImage: String? = null
)

@Entity
data class User(
    @PrimaryKey
    val id: Long? = null,
    val login: String? = "",
    val fullName: String? = "",
    val userType: UserType? = null,
    val profileImage: String? = null
)
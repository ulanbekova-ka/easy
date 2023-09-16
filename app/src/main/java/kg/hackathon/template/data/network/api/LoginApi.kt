package kg.hackathon.template.data.network.api

import kg.hackathon.template.data.network.model.ClientBody
import kg.hackathon.template.data.network.model.LoginBody
import kg.hackathon.template.data.network.model.ShopBody
import kg.hackathon.template.data.network.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/login")
    suspend fun login(@Body loginBody: LoginBody): UserResponse

    @POST("auth/register/client")
    suspend fun registerClient(@Body body: ClientBody): UserResponse

    @POST("auth/register/shop")
    suspend fun registerShop(@Body body: ShopBody): UserResponse
}
package kg.hackathon.template.data.repository

import kg.hackathon.template.data.db.database.AppDB
import kg.hackathon.template.data.network.api.LoginApi
import kg.hackathon.template.data.network.model.ClientBody
import kg.hackathon.template.data.network.model.LoginBody
import kg.hackathon.template.data.network.model.ShopBody
import kg.hackathon.template.data.network.model.User
import kg.hackathon.template.data.network.model.UserResponse
import kg.hackathon.template.data.prefs.UserPrefs
import kg.hackathon.template.data.Dispatcher
import kg.hackathon.template.utils.convertToUser
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val dispatcher: Dispatcher,
    private val api: LoginApi,
    private val prefs: UserPrefs,
    private val db: AppDB
) {

    suspend fun login(login: String, password: String): UserResponse =
        withContext(dispatcher.io()) {
            val body = LoginBody(login, password)
            val response = api.login(body)
            if (!response.token.isNullOrEmpty()) { prefs.token = response.token }
            saveUser(response.convertToUser())
            return@withContext response
        }

    private suspend fun saveUser(user: User) = withContext(dispatcher.io()) {
        db.userDao().deleteUser()
        db.userDao().saveUser(user)
    }

    suspend fun registerClient(body: ClientBody): UserResponse =
        withContext(dispatcher.io()) {
            val response = api.registerClient(body)
            saveUser(response.convertToUser())
            return@withContext response
        }

    suspend fun registerShop(body: ShopBody): UserResponse =
        withContext(dispatcher.io()) {
            val response = api.registerShop(body)
            saveUser(response.convertToUser())
            return@withContext response
        }
}
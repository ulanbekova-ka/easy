package kg.hackathon.template.data.repository

import kg.hackathon.template.data.db.database.AppDB
import kg.hackathon.template.data.network.model.User
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val db: AppDB
) {

    suspend fun fetchUserFromDB(): User? {
        return db.userDao().getUser()
    }
}
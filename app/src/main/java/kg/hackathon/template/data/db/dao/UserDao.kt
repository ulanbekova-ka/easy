package kg.hackathon.template.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.hackathon.template.data.network.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("SELECT * from User")
    suspend fun getUser(): User?

    @Query("DELETE from User")
    suspend fun deleteUser()

}
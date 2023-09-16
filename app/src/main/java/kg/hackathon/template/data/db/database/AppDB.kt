package kg.hackathon.template.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.hackathon.template.data.db.dao.UserDao
import kg.hackathon.template.data.network.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {
        const val DATABASE_NAME = "database"
    }
}
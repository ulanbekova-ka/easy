package kg.hackathon.template.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.hackathon.template.data.db.database.AppDB
import kg.hackathon.template.data.prefs.UserPrefs
import kg.hackathon.template.data.Dispatcher
import kg.hackathon.template.utils.DispatcherImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context) = UserPrefs(context)

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher = DispatcherImpl()

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDB::class.java,
            AppDB.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
}
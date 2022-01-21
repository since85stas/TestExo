package stas.batura.testapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import stas.batura.testapp.data.room.UsersDao
import stas.batura.testapp.data.room.UserDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {

    @Provides
    fun providePressureDao(database: UserDatabase): UsersDao {
        return database.usersDatabaseDao
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): UserDatabase {
        return UserDatabase.getInstance(appContext)
    }

}
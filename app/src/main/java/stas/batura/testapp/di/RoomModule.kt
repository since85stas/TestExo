package stas.batura.testapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import stas.batura.testapp.data.room.VideoDao
import stas.batura.testapp.data.room.VideoDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {

    @Provides
    fun provideVideoDao(videoDatabase: VideoDatabase): VideoDao {
        return videoDatabase.videoDatabaseDao
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): VideoDatabase {
        return VideoDatabase.getInstance(appContext)
    }

}
package stas.batura.testapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import stas.batura.testapp.data.IRepository
import stas.batura.testapp.data.Repository
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepos(repository: Repository): IRepository

}
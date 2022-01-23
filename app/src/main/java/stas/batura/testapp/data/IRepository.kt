package stas.batura.testapp.data

import kotlinx.coroutines.flow.Flow
import stas.batura.testapp.data.room.Video


interface IRepository {

    suspend fun loadData()

    fun getVideos(): Flow<List<Video>>

//    fun getUser(userId: Int): Flow<Video>
//
    fun isDataLoaded(): Flow<Boolean>

    fun dataIsLoaded()
//
//    suspend fun loadUsers()


}
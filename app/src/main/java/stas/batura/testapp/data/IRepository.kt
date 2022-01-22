package stas.batura.testapp.data

import kotlinx.coroutines.flow.Flow
import stas.batura.testapp.data.room.Video


interface IRepository {

    fun getUsers(): Flow<List<Video>>

    fun getUser(userId: Int): Flow<Video>

    fun isLogged(): Flow<Boolean>

    suspend fun loadUsers()


}
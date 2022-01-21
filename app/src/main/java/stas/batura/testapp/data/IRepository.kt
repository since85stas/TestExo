package stas.batura.testapp.data

import kotlinx.coroutines.flow.Flow
import stas.batura.testapp.data.room.User


interface IRepository {

    fun getUsers(): Flow<List<User>>

    fun getUser(userId: Int): Flow<User>

    fun isLogged(): Flow<Boolean>

    suspend fun loadUsers()


}
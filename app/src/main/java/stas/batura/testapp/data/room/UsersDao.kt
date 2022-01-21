package stas.batura.testapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import stas.batura.testapp.data.room.User

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPodcast(user: User): Long

    @Query("SELECT * from users_table ORDER BY userId")
    fun getUsers() : Flow<List<User>>

    @Query("SELECT * FROM users_table WHERE userId = :userId")
    fun getUserId(userId: Int) : Flow<User>

}
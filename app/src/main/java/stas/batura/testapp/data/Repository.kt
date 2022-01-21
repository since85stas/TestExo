package stas.batura.testapp.data

import android.util.Log
import androidx.datastore.DataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import stas.batura.testapp.data.room.UsersDao
import stas.batura.testapp.data.room.User
import stas.batura.testapp.UserPreferences
import stas.batura.testapp.data.net.IRetrofit
import javax.inject.Inject
import javax.inject.Singleton

private val TAG = "Repository.kt"

@Singleton
class Repository @Inject constructor(): IRepository{

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var repositoryJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [repositoryJob], any coroutine started in this uiScope can be cancelled
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android.
     */
    private val repScope = CoroutineScope(Dispatchers.IO + repositoryJob)

    @Inject lateinit var retrofit: IRetrofit

    @Inject lateinit var dataStore: DataStore<UserPreferences>

    @Inject lateinit var usersDao: UsersDao

    init {
        Log.d(TAG, "Rep init: ")
    }

    override suspend fun loadUsers() {
        val users = retrofit.getData()
//        for (user in users.users) {
//            usersDao.insertPodcast(user = User.FromUserResponse.build(user))
//        }
        Log.d(TAG, "loadUsers: ")
    }

    override fun getUsers(): Flow<List<User>> {
        return usersDao.getUsers()
    }

    override fun getUser(userId: Int): Flow<User> {
        return usersDao.getUserId(userId)
    }

    override fun isLogged(): Flow<Boolean> {
        return dataStore.data.map {
            it.isLogged
        }
    }

    private suspend fun setName(name: String) {
        dataStore.updateData { t: UserPreferences ->
            t.toBuilder().setName(name).build()
        }
    }
}
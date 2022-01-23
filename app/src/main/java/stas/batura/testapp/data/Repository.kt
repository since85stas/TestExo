package stas.batura.testapp.data

import android.util.Log
import androidx.datastore.DataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import stas.batura.testapp.data.room.VideoDao
import stas.batura.testapp.data.room.Video
import stas.batura.testapp.UserPreferences
import stas.batura.testapp.data.net.IRetrofit
import stas.batura.testapp.data.net.Links
import stas.batura.testapp.data.net.NetResponse
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "Repository.kt"

@Singleton
class Repository @Inject constructor(): IRepository{

    /**
     * viewModelJob allows us to cancel all coroutines started
     */
    private var repositoryJob = Job()

    private val repScope = CoroutineScope(Dispatchers.IO + repositoryJob)

    @Inject lateinit var retrofit: IRetrofit

    @Inject lateinit var dataStore: DataStore<UserPreferences>

    @Inject lateinit var videoDao: VideoDao

    init {
        Log.d(TAG, "Rep init: ")
    }

    override suspend fun loadData() {
        val data = retrofit.getData()
        saveLinksInDb(data.links)
        Log.d(TAG, "loadUsers: ")
    }

    private suspend fun saveLinksInDb(links: Links) {
        videoDao.insertVideo(Video(id = 0, url = links.single))
        videoDao.insertVideo(Video(id = 1, url = links.splitH))
        videoDao.insertVideo(Video(id = 2, url = links.splitV))
        videoDao.insertVideo(Video(id = 3, url = links.src))
    }

    override fun getVideos(): Flow<List<Video>> {
        return videoDao.getVideos()
    }

//    override fun getUser(userId: Int): Flow<Video> {
//        return videoDao.getUserId(userId)
//    }
//
    override fun isLogged(): Flow<Boolean> {
        return dataStore.data.map {
            it.isDataStored
        }
    }

    override fun dataIsLoaded(){
        repScope.launch {
            dataStore.updateData { t: UserPreferences ->
                t.toBuilder().setIsDataStored(true).build()
            }
        }
    }
}
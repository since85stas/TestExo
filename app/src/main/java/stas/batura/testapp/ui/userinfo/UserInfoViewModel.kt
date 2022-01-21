package stas.batura.testapp.ui.userinfo

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import stas.batura.testapp.R
import stas.batura.testapp.data.IRepository
import stas.batura.testapp.data.room.User

private val TAG = "MainViewModel"

class UserInfoViewModel @ViewModelInject constructor(val repository: IRepository): ViewModel() {

    private val _toastText = MutableLiveData<String>()
    val toastTex: LiveData<String> get() = _toastText

    val _spinner = MutableLiveData<Boolean>(true)
    val spinner: LiveData<Boolean>  get() = _spinner

    private val _userId = MutableLiveData<Int?>(null)
    val userId: LiveData<Int?> get() = _userId

    fun getUser(userId : Int) = repository.getUser(userId).asLiveData()

    init {
        Log.d(TAG, ": $repository")
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                Log.d(TAG, "launchDataLoad: " + error)
                _toastText.value = "Internet problem"
            } finally {
                _spinner.value = false
            }
        }
    }
}
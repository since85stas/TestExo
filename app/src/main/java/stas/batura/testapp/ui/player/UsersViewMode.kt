package stas.batura.testapp.ui.player

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stas.batura.testapp.data.IRepository

private val TAG = "PayViewModel"

class UsersViewModel @ViewModelInject constructor(val repository: IRepository): ViewModel() {

    private val _toastText = MutableLiveData<String>()
    val toastTex: LiveData<String> get() = _toastText

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean> get() = _spinner

    private val _userId = MutableLiveData<Int?>(null)
    val userId: LiveData<Int?> get() = _userId

//    val user = repository.getUser()

    val users = repository.getUsers().asLiveData()

    init {
        Log.d(TAG, ": $repository")
        loadData()
    }

    private fun loadData() {
        launchDataLoad {
            repository.loadData()
        }
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

    fun onItemClick(userId: Int) {
        _userId.value = userId
        _userId.value = null
    }
}
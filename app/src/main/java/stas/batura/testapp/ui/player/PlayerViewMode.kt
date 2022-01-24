package stas.batura.testapp.ui.player

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stas.batura.testapp.data.IRepository

private val TAG = "PayViewModel"

class PlayerViewModel @ViewModelInject constructor(val repository: IRepository): ViewModel() {

    private val _toastText = MutableLiveData<String>()
    val toastTex: LiveData<String> get() = _toastText

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean> get() = _spinner

    val isDataReady = repository.isDataLoaded().asLiveData()

    val users = repository.getVideos().asLiveData()

    init {
        Log.d(TAG, ": $repository")
        loadData()
    }

    private fun loadData() {
        launchDataLoad {
            repository.loadData()

            // просто для имитации длительной загрузки
            delay(500)
        }
    }

    /**
     * пока идет загрузка показываем progressBar, после выключаем
     * в случаи ошибки показываем тост
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                Log.d(TAG, "launchDataLoad: " + error)
                _toastText.value = "Internet problem: try later"
            } finally {
                _spinner.value = false
            }
        }
    }

}
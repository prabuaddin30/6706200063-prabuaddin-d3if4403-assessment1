package org.d3if0063.garbageanywhere.ui.historiapp
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0063.garbageanywhere.MainActivity
import org.d3if0063.garbageanywhere.database.GarbageDao
import org.d3if0063.garbageanywhere.database.GarbageDb
import org.d3if0063.garbageanywhere.network.GarbageApi
import org.d3if0063.garbageanywhere.network.GarbageStatus
import org.d3if0063.garbageanywhere.network.WorkerClass
import java.lang.Exception
import java.util.concurrent.TimeUnit


class HistoryViewModel(private val db: GarbageDao) : ViewModel() {
    val data = db.getLastGarbage()
    private val dataGarbage = MutableLiveData<List<GarbageDb>>()
    private val status = MutableLiveData<GarbageStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData(){
        viewModelScope.launch(Dispatchers.IO){
            status.postValue(GarbageStatus.LOADING)
            try{
                dataGarbage.postValue(GarbageApi.service.getGarbage())
                status.postValue(GarbageStatus.SUCCESS)
            } catch (e: Exception){
                Log.d("HistoryViewModel", "Failure: ${e.message}")
                status.postValue(GarbageStatus.FAILED)
            }
        }
    }

    fun getStatus(): LiveData<GarbageStatus> = status

    fun scheduleUpdater(app: Application){
        val request = OneTimeWorkRequestBuilder<WorkerClass>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(app).enqueueUniqueWork(MainActivity.CHANNEL_ID,
        ExistingWorkPolicy.REPLACE, request)
    }

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.clearData()
        }
    }
}
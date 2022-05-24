package org.d3if0063.garbageanywhere.ui.historiapp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0063.garbageanywhere.database.GarbageDao


class HistoryViewModel(private val db: GarbageDao) : ViewModel() {
    val data = db.getLastGarbage()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.clearData()
        }
    }
}
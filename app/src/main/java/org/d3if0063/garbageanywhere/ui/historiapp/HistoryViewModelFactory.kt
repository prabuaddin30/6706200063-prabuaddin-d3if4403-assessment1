package org.d3if0063.garbageanywhere.ui.historiapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0063.garbageanywhere.database.GarbageDao
import org.d3if0063.garbageanywhere.database.GarbageDb
import java.lang.IllegalArgumentException

class HistoryViewModelFactory(
    private val db: GarbageDao
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
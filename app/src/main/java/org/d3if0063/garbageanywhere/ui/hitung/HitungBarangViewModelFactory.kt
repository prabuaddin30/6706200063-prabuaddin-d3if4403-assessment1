package org.d3if0063.garbageanywhere.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0063.garbageanywhere.database.GarbageDao
import java.lang.IllegalArgumentException

class HitungBarangViewModelFactory(
    private val db: GarbageDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HitungBarangViewModel::class.java)){
            return HitungBarangViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
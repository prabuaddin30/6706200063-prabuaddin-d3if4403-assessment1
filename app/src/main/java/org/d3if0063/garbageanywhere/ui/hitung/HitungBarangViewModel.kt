package org.d3if0063.garbageanywhere.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if0063.garbageanywhere.database.GarbageDao
import org.d3if0063.garbageanywhere.database.GarbageDb
import org.d3if0063.garbageanywhere.model.HasilTimbang
import org.d3if0063.garbageanywhere.model.KategoriJual
import org.d3if0063.garbageanywhere.model.hitungBarang


class HitungBarangViewModel(private val db: GarbageDao) : ViewModel() {
    private val hasilTimbang = MutableLiveData<HasilTimbang?>()
    private val navigasi = MutableLiveData<KategoriJual?>()

    fun hitungBarang(nama: String, berat: Float, jumlah: Float, jenis: Boolean){
        val dataBarang = GarbageDb(
            nama = nama,
            berat = berat,
            jumlah = jumlah,
            jenis = jenis
        )
        hasilTimbang.value = dataBarang.hitungBarang()

        viewModelScope.launch { withContext(Dispatchers.IO){
            db.insert(dataBarang)
            }
        }
    }

    fun getHasilTimbang(): LiveData<HasilTimbang?> = hasilTimbang

    fun startNavigation(){
        navigasi.value = hasilTimbang.value?.kategori
    }

    fun endNavigation(){
        navigasi.value = null
    }

    fun getNavigation(): LiveData<KategoriJual?> = navigasi
}
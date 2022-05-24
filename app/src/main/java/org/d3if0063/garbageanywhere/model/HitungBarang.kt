package org.d3if0063.garbageanywhere.model

import org.d3if0063.garbageanywhere.database.GarbageDb

fun GarbageDb.hitungBarang(): HasilTimbang{
    val garbage = berat / (jumlah * jumlah)
    val kategoriJual = if(jenis){
        when{
            garbage < 40.0 -> KategoriJual.TIDAKLAYAK
            garbage >= 45.0 -> KategoriJual.LAYAK
            else -> KategoriJual.NEGOSIASI
        }
    } else {
        when {
            garbage < 20.0 -> KategoriJual.TIDAKLAYAK
            garbage >= 25.0 -> KategoriJual.LAYAK
            else -> KategoriJual.NEGOSIASI
        }
    }
    return HasilTimbang(garbage, kategoriJual)
}
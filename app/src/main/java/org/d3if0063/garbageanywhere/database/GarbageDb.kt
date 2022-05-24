package org.d3if0063.garbageanywhere.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "garbage")
data class GarbageDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var nama: String,
    var jumlah: Float,
    var berat: Float,
    var jenis: Boolean
)

@Dao
interface GarbageDao{
    @Insert
    fun insert(garbage: GarbageDb)

    @Query("SELECT * FROM garbage ORDER BY id DESC")
    fun getLastGarbage(): LiveData<List<GarbageDb>>

    @Query("DELETE FROM garbage")
    fun clearData()
}

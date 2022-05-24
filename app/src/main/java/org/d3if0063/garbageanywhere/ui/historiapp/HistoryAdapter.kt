package org.d3if0063.garbageanywhere.ui.historiapp

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import org.d3if0063.garbageanywhere.R
import org.d3if0063.garbageanywhere.database.GarbageDb
import org.d3if0063.garbageanywhere.databinding.ItemHistoryBinding
import org.d3if0063.garbageanywhere.model.KategoriJual
import org.d3if0063.garbageanywhere.model.hitungBarang
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<GarbageDb, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<GarbageDb>() {
                override fun areItemsTheSame(oldItem: GarbageDb, newItem: GarbageDb): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: GarbageDb, newItem: GarbageDb): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoryBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MM yyyy", Locale("id", "ID"))

        fun bind(item: GarbageDb) = with(binding) {
            val hasilTimbang = item.hitungBarang()
            kategoriTextView.text = hasilTimbang.kategori.toString().substring(0, 1)
            val colorRes = when (hasilTimbang.kategori) {
                KategoriJual.LAYAK -> R.color.green_young
                KategoriJual.TIDAKLAYAK -> R.color.red
                else -> R.color.teal_700
            }

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            garbageTextView.text = root.context.getString(R.string.hasil_x,
            hasilTimbang.timbang, hasilTimbang.kategori.toString())

            val jenis = root.context.getString(if (item.jenis) R.string.besi else R.string.plastik)
            dataTextView.text = root.context.getString(R.string.data_x,
            item.berat, item.jumlah, jenis)
        }
    }
}
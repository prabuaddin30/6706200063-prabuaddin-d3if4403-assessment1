package org.d3if0063.garbageanywhere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import org.d3if0063.garbageanywhere.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener{
            tentukanBarang()
        }
    }

    override fun onCreateOptionsMenu(pengaturan: Menu): Boolean {
        pengaturan.add(Menu.NONE, 1, Menu.NONE, "Night Mode")
        pengaturan.add(Menu.NONE, 2, Menu.NONE, "Day Mode")
        return super.onCreateOptionsMenu(pengaturan)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentukanBarang(){
        val berat = binding.beratEditText.text.toString()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(this, R.string.berat_error, Toast.LENGTH_LONG).show()
            return
        }

        val jumlah = binding.jumlahEditText.text.toString()
        if(TextUtils.isEmpty(jumlah)){
            Toast.makeText(this, R.string.jumlah_error, Toast.LENGTH_LONG).show()
            return
        }
        val jumlahBarang = jumlah.toFloat()
        val selectedId = binding.radioGrup.checkedRadioButtonId
        if (selectedId == -1){
            Toast.makeText(this, R.string.kategori_error, Toast.LENGTH_LONG).show()
            return
        }
        val layakTidak = selectedId == R.id.besiRadioButton
        val hasil = berat.toFloat() * jumlahBarang
        val kategori = getKategori(hasil, layakTidak)

        binding.layakJualView.text = getString(R.string.nilai_layak, hasil)
        binding.categoryView.text = getString(R.string.kategori, kategori)
    }

    private fun getKategori(hasil: Float, layakTidak: Boolean): String{
        val kategorii = if (layakTidak){
            when{
                hasil < 40.0 -> R.string.tidak_layak
                hasil >= 45.0 -> R.string.layak_jual
                else -> R.string.negosiasi
            }
        } else {
            when {
                hasil < 20.0 -> R.string.tidak_layak
                hasil >= 25.0 -> R.string.nilai_layak
                else -> R.string.negosiasi
            }
        }
        return getString(kategorii)
    }
}
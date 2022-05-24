package org.d3if0063.garbageanywhere.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.d3if0063.garbageanywhere.R
import org.d3if0063.garbageanywhere.database.GarbageClass
import org.d3if0063.garbageanywhere.databinding.FragmentHitungBarangBinding
import org.d3if0063.garbageanywhere.model.HasilTimbang
import org.d3if0063.garbageanywhere.model.KategoriJual

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBarangBinding

    private val viewModel: HitungBarangViewModel by lazy {
        val db = GarbageClass.getInstance(requireContext())
        val factory = HitungBarangViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungBarangViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHitungBarangBinding.inflate(layoutInflater, container, false )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(pengaturan: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(pengaturan, inflater)
        inflater.inflate(R.menu.options_menu, pengaturan)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){

            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historyFragment)
                return true
            }

            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutAppFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitButton.setOnClickListener { tentukanBarang() }
        binding.saranButton.setOnClickListener { viewModel.startNavigation() }
        binding.shareButton.setOnClickListener { shareData() }
        viewModel.getHasilTimbang().observe(requireActivity(), { showHasil(it)})
        viewModel.getNavigation().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections.actionHitungFragmentToSaranAppFragment(it))
            viewModel.endNavigation()
        })
    }

    private fun shareData(){
        val selectedId = binding.radioGrup.checkedRadioButtonId
        val jenis = if (selectedId == R.id.besiRadioButton)
            getString(R.string.besi)
        else
            getString(R.string.bagikan_template)
        val message = getString(R.string.bagikan_template,
        binding.beratEditText.text,
        binding.jumlahEditText.text,
        jenis,
        binding.layakJualView.text,
        binding.categoryView.text)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }
    }


    private fun tentukanBarang(){

        val nama = binding.namaEditText.text.toString()
        if (TextUtils.isEmpty(nama)){
            Toast.makeText(context, R.string.nama_barang_error, Toast.LENGTH_LONG).show()
            return
        }

        val berat = binding.beratEditText.text.toString()
        if(TextUtils.isEmpty(berat)){
            Toast.makeText(context, R.string.berat_error, Toast.LENGTH_LONG).show()
            return
        }

        val jumlah = binding.jumlahEditText.text.toString()
        if(TextUtils.isEmpty(jumlah)){
            Toast.makeText(context, R.string.jumlah_error, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGrup.checkedRadioButtonId
        if (selectedId == -1){
            Toast.makeText(context, R.string.kategori_error, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungBarang(
            nama,
            berat.toFloat(),
            jumlah.toFloat(),
            selectedId == R.id.besiRadioButton
        )
    }

    private fun getKategori(timbang: Float, kategoriJual: Boolean): Int {
        val kategori = if (kategoriJual){
            when{
                timbang < 40.0 -> R.string.tidak_layak
                timbang >= 45.0 -> R.string.layak_jual
                else -> R.string.negosiasi
            }
        } else {
            when {
                timbang < 20.0 -> R.string.tidak_layak
                timbang >= 25.0 -> R.string.nilai_layak
                else -> R.string.negosiasi
            }
        }
        return kategori
    }


    private fun getCategoryLabel(kategoriJual: KategoriJual): String{
        val stringRes = when(kategoriJual){
            KategoriJual.TIDAKLAYAK -> R.string.tidak_layak
            KategoriJual.LAYAK -> R.string.layak_jual
            KategoriJual.NEGOSIASI -> R.string.negosiasi
        }
        return getString(stringRes)
    }

    private fun showHasil(result: HasilTimbang?){
        if (result == null) return
        binding.categoryView.text = getString(R.string.kategori_x, getCategoryLabel(result.kategori))
        binding.buttonGroup.visibility = View.VISIBLE
    }
}



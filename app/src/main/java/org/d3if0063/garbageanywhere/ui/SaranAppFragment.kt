package org.d3if0063.garbageanywhere.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if0063.garbageanywhere.R
import org.d3if0063.garbageanywhere.databinding.FragmentSaranAplikasiBinding
import org.d3if0063.garbageanywhere.model.KategoriJual

class SaranAppFragment : Fragment() {
    private lateinit var binding: FragmentSaranAplikasiBinding
    private val args: SaranAppFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaranAplikasiBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriJual){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori){
            KategoriJual.LAYAK -> {
                actionBar?.title = getString(R.string.judul_layak)
                binding.textView.text = getString(R.string.layak_hasil)
            }
            KategoriJual.NEGOSIASI -> {
                actionBar?.title = getString(R.string.judul_nego)
                binding.textView.text = getString(R.string.nego_hasil)
            }
            KategoriJual.TIDAKLAYAK -> {
                actionBar?.title = getString(R.string.judul_tidak_layak)
                binding.textView.text = getString(R.string.tidak_layak_hasil)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        updateUI(args.kategori)
    }
}




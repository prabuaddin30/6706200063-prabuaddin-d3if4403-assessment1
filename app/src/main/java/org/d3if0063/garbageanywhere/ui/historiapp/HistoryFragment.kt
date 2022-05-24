package org.d3if0063.garbageanywhere.ui.historiapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if0063.garbageanywhere.R
import org.d3if0063.garbageanywhere.databinding.FragmentHistoryBinding
import org.d3if0063.garbageanywhere.database.GarbageClass

class HistoryFragment : Fragment() {
    private val viewModel: HistoryViewModel by lazy {
        val db = GarbageClass.getInstance(requireContext())
        val factory = HistoryViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var myAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoryAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_hapus){
            hapusData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusData(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.confirm_delete)
            .setPositiveButton(getString(R.string.hapus_histori)){ _, _ ->
                viewModel.hapusData()
            }
            .setNegativeButton(getString(R.string.batal)){ dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}
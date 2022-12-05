package org.wit.animarker.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.animarker.adapters.AnimarkerAdapter
import org.wit.animarker.adapters.AnimarkerListener
import org.wit.animarker.databinding.FragmentListBinding
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.AnimarkerModel
import timber.log.Timber

class ListFragment : Fragment(), AnimarkerListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    lateinit var app: MainApp
    private lateinit var listViewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("ON CREATE LIST FRAGMENT")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableAnimarkers.observe(viewLifecycleOwner, Observer { animarkers ->
            animarkers?.let { render(animarkers) }
        })
        return binding.root
    }

    private fun render(animarkers: List<AnimarkerModel>) {
        binding.recyclerView.adapter = AnimarkerAdapter(animarkers, this)
        if (animarkers.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onAnimarkerClick(animarker: AnimarkerModel) {
        //TODO when animarker is clicked go to view animarker fragment! have edit and delete option.
    }

    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
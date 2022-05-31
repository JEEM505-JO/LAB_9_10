package com.uni.lab9_10.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.lab9_10.DataBase
import com.uni.lab9_10.R
import com.uni.lab9_10.Repository
import com.uni.lab9_10.databinding.FragmentContactBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class Contact : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var adaptercontact: AdapterContact
    private lateinit var viewmodel: ViewModelContact
    private val scope = CoroutineScope(SupervisorJob())
    private val database by lazy { DataBase.getDatabase(requireContext(), scope) }
    private val repository by lazy { Repository(database.modelDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.flbAdd.setOnClickListener {
            it.findNavController().navigate(R.id.action_contact2_to_addContact)
        }


        /*binding.buttonbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    val item = it?.actionView as SearchView
                    item.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            Toast.makeText(context, "MENSAJE $p0", Toast.LENGTH_SHORT).show()
                            return true
                        }
                        override fun onQueryTextChange(p0: String?): Boolean {
                            return false
                        }
                    })
                    true
                }
                R.id.delete -> {
                    viewmodel.deleteall()
                    true
                }
                else -> {
                    false
                }
            }
        }*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ContactViewModelFactory(repository)
        viewmodel = ViewModelProvider(this, factory)[ViewModelContact::class.java]
        adaptercontact = AdapterContact()
        val recycler = binding.recyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        viewmodel.allContact.observe(viewLifecycleOwner) { contact ->
            contact.let { adaptercontact.submitList(it) }
        }
        adaptercontact.notifyDataSetChanged()
        recycler.adapter = adaptercontact

    }
}



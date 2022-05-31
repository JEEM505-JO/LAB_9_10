package com.uni.lab9_10.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uni.lab9_10.DataBase
import com.uni.lab9_10.Model
import com.uni.lab9_10.R
import com.uni.lab9_10.Repository
import com.uni.lab9_10.databinding.FragmentAddContactBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AddContact : Fragment() {
    private lateinit var viewmodeladd: ViewModelAddContact
    private lateinit var factory: AddContactViewModelFactory
    private val scope = CoroutineScope(SupervisorJob())
    private val database by lazy { DataBase.getDatabase(requireContext(), scope) }
    private val repository by lazy { Repository(database.modelDao()) }
    private lateinit var binding: FragmentAddContactBinding


    private var nombre: String = ""
    private var numero: String = ""
    private var referencia: String = ""
    private var genereteid = 0
    private var sms: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        factory = AddContactViewModelFactory(repository)
        viewmodeladd = ViewModelProvider(this, factory)[ViewModelAddContact::class.java]
        binding.lifecycleOwner = this
        binding.viewmodel = viewmodeladd

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            numero = it.getString("Numero") as String
            nombre = it.getString("Nombre") as String
            referencia = it.getString("Referencia") as String
            genereteid = it.getInt("ID")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (nombre == "" && numero == "" && referencia == "") {
            println("validacion de insert")
            viewmodeladd.nombre.observe(viewLifecycleOwner) {
                nombre = it
            }
            viewmodeladd.numero.observe(viewLifecycleOwner) {
                numero = it
            }
            viewmodeladd.referencia.observe(viewLifecycleOwner) {
                referencia = it
            }
            viewmodeladd.sms.observe(viewLifecycleOwner) {
                sms = it
            }
            genereteid = viewmodeladd.generateid()
            viewmodeladd.valid.observe(viewLifecycleOwner) {
                if (it) {
                    println("ENTRO EN VERDADERO")
                    val model = Model(genereteid, nombre, numero, referencia)
                    viewmodeladd.insert(model).let { job ->
                        if (job.isActive) {
                            findNavController().navigate(R.id.action_addContact_to_contact2)
                        }
                    }
                } else {
                    println("ENTRO EN FALSO")
                    Toast.makeText(
                        requireContext(),
                        viewmodeladd.sms.value.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            binding.btndelete.isVisible = true
            println("validacion de update")
            viewmodeladd.seddata(nombre, numero, referencia)
            viewmodeladd.valid.observe(viewLifecycleOwner) {
                if (it) {
                    println("ENTRO EN UPDATE")
                    val model1 = Model(genereteid, nombre, numero, referencia)
                    viewmodeladd.update(model1)
                    findNavController().navigate(R.id.action_addContact_to_contact2)
                } else {
                    println("ENTRO EN FALSO")
                    Toast.makeText(
                        requireContext(),
                        viewmodeladd.sms.value.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.btndelete.setOnClickListener {
                viewmodeladd.deleteitem(genereteid).let {
                    if (it.isActive){
                        findNavController().navigate(R.id.action_addContact_to_contact2)
                        println("ENTRO EN DELETE")
                    }
                }
            }
        }
    }
}




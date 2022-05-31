package com.uni.lab9_10.ui

import androidx.lifecycle.*
import com.uni.lab9_10.Model
import com.uni.lab9_10.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ViewModelAddContact(private val repository: Repository) : ViewModel() {

    val nombre = MutableLiveData<String>()
    private val _nombre: LiveData<String> get() = nombre
    val numero = MutableLiveData<String>()
    private val _numero: LiveData<String> get() = numero
    val referencia = MutableLiveData<String>()
    private val _referencia: LiveData<String> get() = referencia
    val valid = MutableLiveData<Boolean>()
    val sms = MutableLiveData<String>()

    fun pushdata() {
        if (nombre.value?.trim().isNullOrEmpty()
            && numero.value?.trim().isNullOrEmpty()
            && referencia.value?.trim().isNullOrEmpty()) {
            valid.postValue(false)
            sms.postValue("Llene todas las casillas mostradas")
        }else if(numero.value?.length!! >= 8){
            valid.postValue(false)
            sms.postValue("Numero mayor a 8 digitos")
        }

        else{
            numero.postValue(_numero.value)
            nombre.postValue(_nombre.value)
            referencia.postValue(_referencia.value)
            valid.postValue(true)
        }
    }

    fun seddata(datanombre : String, datanumero : String, datareferencia: String){
        nombre.value = datanombre
        numero.value = datanumero
        referencia.value = datareferencia
    }
    fun generateid():Int{
        val ramdon = (0..20).random()
        return ramdon
    }

    fun insert(model: Model) = viewModelScope.launch {
        repository.insert(model)
    }

    fun update(model: Model) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(model)
    }
    fun deleteitem(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteContact(id)
    }
}

class AddContactViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelAddContact::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelAddContact(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}
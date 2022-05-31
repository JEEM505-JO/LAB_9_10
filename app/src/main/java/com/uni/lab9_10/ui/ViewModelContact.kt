package com.uni.lab9_10.ui

import androidx.lifecycle.*
import com.uni.lab9_10.Model
import com.uni.lab9_10.Repository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ViewModelContact(private val repository: Repository) : ViewModel() {
    val allContact: LiveData<List<Model>> = repository.allmodel.asLiveData()

    fun getonecontact(model: Model) = viewModelScope.launch{
         repository.getContact(model).asLiveData()
    }
    fun deleteall() = viewModelScope.launch {
        repository.deleteall()
    }

}

class ContactViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelContact::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelContact(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}